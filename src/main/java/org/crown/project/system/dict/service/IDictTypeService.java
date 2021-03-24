package org.crown.project.system.dict.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.framework.web.domain.Ztree;
import org.crown.project.system.dict.domain.DictType;

/**
 * Dictionary Business layer
 *
 * @author Crown
 */
public interface IDictTypeService extends BaseService<DictType> {

    /**
     * Query dictionary type by condition
     *
     * @param dictType Dictionary type information
     * @return Dictionary type collection information
     */
    List<DictType> selectDictTypeList(DictType dictType);

    /**
     * Batch delete dictionary types
     *
     * @param ids Data to be deleted
     * @return result
     * @throws Exception abnormal
     */
    boolean deleteDictTypeByIds(String ids);

    /**
     * Modify and save dictionary type information
     *
     * @param dictType Dictionary type information
     * @return result
     */
    boolean updateDictType(DictType dictType);

    /**
     * Check whether the dictionary type is unique
     *
     * @param dictType Dictionary type
     * @return result
     */
    boolean checkDictTypeUnique(DictType dictType);

    /**
     * Query dictionary type tree
     *
     * @param dictType Dictionary type
     * @return All dictionary types
     */
    List<Ztree> selectDictTree(DictType dictType);

}
