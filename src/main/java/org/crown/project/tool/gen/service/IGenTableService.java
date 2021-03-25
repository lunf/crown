package org.crown.project.tool.gen.service;

import java.util.List;
import java.util.Map;

import org.crown.framework.service.BaseService;
import org.crown.project.tool.gen.domain.GenTable;

/**
 * 业务 服务层
 *
 * @author Crown
 */
public interface IGenTableService extends BaseService<GenTable> {

    /**
     * Query business list
     *
     * @param genTable Business information
     * @return Business collection
     */
    List<GenTable> selectGenTableList(GenTable genTable);

    /**
     * Query database list
     *
     * @param genTable Business information
     * @return Database table collection
     */
    List<GenTable> selectDbTableList(GenTable genTable);

    /**
     * Query database list
     *
     * @param tableNames Table name group
     * @return Database table collection
     */
    List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * Query business information
     *
     * @param id Business ID
     * @return Business information
     */
    GenTable selectGenTableById(Long id);

    /**
     * Modify business
     *
     * @param genTable Business information
     * @return result
     */
    void updateGenTable(GenTable genTable);

    /**
     * Delete business information
     *
     * @param ids ID of the data to be deleted
     * @return result
     */
    void deleteGenTableByIds(String ids);

    /**
     * Import table structure
     *
     * @param tableList Import table list
     */
    void importGenTable(List<GenTable> tableList);

    /**
     * Preview code
     *
     * @param tableId Table number
     * @return Preview data list
     */
    Map<String, String> previewCode(Long tableId);

    /**
     * Generate code
     *
     * @param tableName Table name
     * @return data
     */
    byte[] generatorCode(String tableName);

    /**
     * Batch code generation
     *
     * @param tableNames Table array
     * @return data
     */
    byte[] generatorCode(String[] tableNames);

    /**
     * Modify and save parameter verification
     *
     * @param genTable Business information
     */
    void validateEdit(GenTable genTable);
}
