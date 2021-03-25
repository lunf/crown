package org.crown.project.tool.gen.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.crown.common.cons.Constants;
import org.crown.common.utils.StringUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.exception.Crown2Exception;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.utils.ApiAssert;
import org.crown.project.tool.gen.GenConstants;
import org.crown.project.tool.gen.domain.GenTable;
import org.crown.project.tool.gen.domain.GenTableColumn;
import org.crown.project.tool.gen.mapper.GenTableMapper;
import org.crown.project.tool.gen.util.GenUtils;
import org.crown.project.tool.gen.util.VelocityInitializer;
import org.crown.project.tool.gen.util.VelocityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * Business service layer implementation
 *
 * @author Crown
 */
@Service
@Slf4j
public class GenTableServiceImpl extends BaseServiceImpl<GenTableMapper, GenTable> implements IGenTableService {

    @Autowired
    private IGenTableColumnService genTableColumnService;

    @Override
    public GenTable selectGenTableById(Long id) {
        GenTable genTable = baseMapper.selectGenTableById(id);
        setTableFromOptions(genTable);
        return genTable;
    }

    @Override
    public List<GenTable> selectGenTableList(GenTable genTable) {
        return query().like(StringUtils.isNotEmpty(genTable.getTableName()), GenTable::getTableName, genTable.getTableName())
                .like(StringUtils.isNotEmpty(genTable.getTableComment()), GenTable::getTableComment, genTable.getTableComment()).list();

    }

    @Override
    public List<GenTable> selectDbTableList(GenTable genTable) {
        return baseMapper.selectDbTableList(genTable);
    }

    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames) {
        return baseMapper.selectDbTableListByNames(tableNames);
    }

    @Override
    @Transactional
    public void updateGenTable(GenTable genTable) {
        String options = JSON.toJSONString(genTable.getParams());
        genTable.setOptions(options);
        if (updateById(genTable)) {
            genTableColumnService.updateBatchById(genTable.getColumns());
        }
    }

    @Override
    @Transactional
    public void deleteGenTableByIds(String ids) {
        delete().in(GenTable::getTableId, StringUtils.split2List(ids)).execute();
        genTableColumnService.deleteGenTableColumnByIds(ids);
    }

    @Override
    @Transactional
    public void importGenTable(List<GenTable> tableList) {
        String operName = ShiroUtils.getLoginName();
        for (GenTable table : tableList) {
            try {
                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                if (save(table)) {
                    // Save column information
                    List<GenTableColumn> genTableColumns = genTableColumnService.selectDbTableColumnsByName(tableName);
                    for (GenTableColumn column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        genTableColumnService.save(column);
                    }
                }
            } catch (Exception e) {
                log.error("Table Name " + table.getTableName() + " Import failed：", e);
                ApiAssert.failure(ErrorCodeEnum.GEN_IMPORT_TABLE_ERROR.overrideMsg("Generate code table name[" + table.getTableName() + "]，Import error"));
            }
        }
    }

    @Override
    public Map<String, String> previewCode(Long tableId) {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // Query form information
        GenTable table = baseMapper.selectGenTableById(tableId);
        // Query column information
        List<GenTableColumn> columns = table.getColumns();
        setPkColumn(table, columns);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // Get a list of templates
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // Render template
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

    @Override
    public byte[] generatorCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * Query table information and generate code
     */
    private void generatorCode(String tableName, ZipOutputStream zip) {
        // Query form information
        GenTable table = baseMapper.selectGenTableByName(tableName);
        // Query column information
        List<GenTableColumn> columns = table.getColumns();
        setPkColumn(table, columns);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // Get a list of templates
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // Render template
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try {
                // Add to zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                log.error("Failed to render template, table name：" + table.getTableName(), e);
            }
        }
    }

    @Override
    public void validateEdit(GenTable genTable) {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
            String options = JSON.toJSONString(genTable.getParams());
            JSONObject paramsObj = JSONObject.parseObject(options);
            if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
                throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "Tree code field cannot be empty");
            } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
                throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "The tree parent code field cannot be empty");
            } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
                throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "The tree name field cannot be empty");
            }
        }
    }

    /**
     * Set the primary key column information
     *
     * @param table   Business table information
     * @param columns Business field list
     */
    public void setPkColumn(GenTable table, List<GenTableColumn> columns) {
        for (GenTableColumn column : columns) {
            if (column.isPk()) {
                table.setPkColumn(column);
                break;
            }
        }
        if (StringUtils.isNull(table.getPkColumn())) {
            table.setPkColumn(columns.get(0));
        }
    }

    /**
     * Set the code to generate other option values
     *
     * @param genTable Generated object after setting
     */
    public void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
        if (StringUtils.isNotNull(paramsObj)) {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
        }
    }
}