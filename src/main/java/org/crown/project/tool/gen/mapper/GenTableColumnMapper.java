package org.crown.project.tool.gen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.tool.gen.domain.GenTableColumn;

/**
 * Business field data layer
 *
 * @author Crown
 */
@Mapper
public interface GenTableColumnMapper extends BaseMapper<GenTableColumn> {

    /**
     * Query column information based on table name
     *
     * @param tableName Table name
     * @return Column information
     */
    List<GenTableColumn> selectDbTableColumnsByName(String tableName);

}