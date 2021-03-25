package org.crown.project.tool.gen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.tool.gen.domain.GenTable;

/**
 * Business data layer
 *
 * @author Crown
 */
@Mapper
public interface GenTableMapper extends BaseMapper<GenTable> {

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
     * Query table ID business information
     *
     * @param id Business ID
     * @return Business information
     */
    GenTable selectGenTableById(Long id);

    /**
     * Query table name business information
     *
     * @param tableName Table name
     * @return Business information
     */
    GenTable selectGenTableByName(String tableName);

}