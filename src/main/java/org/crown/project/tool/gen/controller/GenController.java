package org.crown.project.tool.gen.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.StringUtils;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.TableData;
import org.crown.project.tool.gen.domain.GenTable;
import org.crown.project.tool.gen.domain.GenTableColumn;
import org.crown.project.tool.gen.service.IGenTableColumnService;
import org.crown.project.tool.gen.service.IGenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Code generation operation processing
 *
 * @author Crown
 */
@Controller
@RequestMapping("/tool/gen")
public class GenController extends WebController {

    private final String prefix = "tool/gen";

    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IGenTableColumnService genTableColumnService;

    @RequiresPermissions("tool:gen:view")
    @GetMapping
    public String gen() {
        return prefix + "/gen";
    }

    /**
     * Query code generation list
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/list")
    @ResponseBody
    public TableData<GenTable> list(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return getTableData(list);
    }

    /**
     * Query database list
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/db/list")
    @ResponseBody
    public TableData<GenTable> dataList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectDbTableList(genTable);
        return getTableData(list);
    }

    /**
     * Query the list of data table fields
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/column/list")
    @ResponseBody
    public TableData<GenTableColumn> columnList(GenTableColumn genTableColumn) {
        TableData dataInfo = new TableData();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(genTableColumn.getTableId());
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return getTableData(list);
    }

    /**
     * Import table structure
     */
    @RequiresPermissions("tool:gen:list")
    @GetMapping("/importTable")
    public String importTable() {
        return prefix + "/importTable";
    }

    /**
     * Import table structure (save)
     */
    @RequiresPermissions("tool:gen:list")
    @Log(title = "Code generation", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    @ResponseBody
    public void importTableSave(String tables) {
        String[] tableNames = StringUtils.split2Array(tables);
        // Query form information
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);
    }

    /**
     * Modify code generation business
     */
    @GetMapping("/edit/{tableId}")
    public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        GenTable table = genTableService.selectGenTableById(tableId);
        mmap.put("table", table);
        return prefix + "/edit";
    }

    /**
     * Modify and save code generation business
     */
    @RequiresPermissions("tool:gen:edit")
    @Log(title = "Code generation", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated GenTable genTable) {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
    }

    @RequiresPermissions("tool:gen:remove")
    @Log(title = "Code generation", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {
        genTableService.deleteGenTableByIds(ids);
    }

    /**
     * Preview code
     */
    @RequiresPermissions("tool:gen:preview")
    @GetMapping("/preview/{tableId}")
    @ResponseBody
    public Map<String, String> preview(@PathVariable("tableId") Long tableId) {
        return genTableService.previewCode(tableId);
    }

    /**
     * Generate code
     */
    @RequiresPermissions("tool:gen:code")
    @Log(title = "Code generation", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genTableService.generatorCode(tableName);
        genCode(response, data);
    }

    /**
     * Batch code generation
     */
    @RequiresPermissions("tool:gen:code")
    @Log(title = "Code generation", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = StringUtils.split2Array(tables);
        byte[] data = genTableService.generatorCode(tableNames);
        genCode(response, data);
    }

    /**
     * Generate zip file
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"Crown.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}