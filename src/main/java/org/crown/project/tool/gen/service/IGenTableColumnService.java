package org.crown.project.tool.gen.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.tool.gen.domain.GenTableColumn;

/**
 * Business field Service layer
 *
 * @author Crown
 */
public interface IGenTableColumnService extends BaseService<GenTableColumn> {

    /**
     * Query the list of business fields
     *
     * @param tableId
     * @return Business field collection
     */
    List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * Delete business field information
     *
     * @param ids ID of the data to be deleted
     * @return result
     */
    boolean deleteGenTableColumnByIds(String ids);

    /**
     * Query column information based on table name
     *
     * @param tableName Table name
     * @return Column information
     */
    List<GenTableColumn> selectDbTableColumnsByName(String tableName);
}
