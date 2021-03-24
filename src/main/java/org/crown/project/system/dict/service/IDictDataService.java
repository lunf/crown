package org.crown.project.system.dict.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.system.dict.domain.DictData;

/**
 * Dictionary Business layer
 *
 * @author Crown
 */
public interface IDictDataService extends BaseService<DictData> {

    /**
     * Paging query dictionary data according to conditions
     *
     * @param dictData Dictionary data information
     * @return Dictionary data collection information
     */
    List<DictData> selectDictDataList(DictData dictData);

    /**
     * Query dictionary data according to dictionary type
     *
     * @param dictType Dictionary type
     * @return Dictionary data collection information
     */
    List<DictData> selectDictDataByType(String dictType);

    /**
     * Query dictionary data information according to dictionary type and dictionary key value
     *
     * @param dictType  Dictionary type
     * @param dictValue Dictionary key
     * @return Dictionary tag
     */
    String selectDictLabel(String dictType, String dictValue);

}
