package org.crown.framework.web.service;

import java.util.List;

import org.crown.project.system.dict.domain.DictData;
import org.crown.project.system.dict.service.IDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * html calls thymeleaf to implement dictionary reading
 *
 * @author Crown
 */
@Service("dict")
public class DictService {

    @Autowired
    private IDictDataService dictDataService;

    /**
     * Query dictionary data information according to dictionary type
     *
     * @param dictType Dictionary type
     * @return Parameter key value
     */
    public List<DictData> getType(String dictType) {
        return dictDataService.selectDictDataByType(dictType);
    }

    /**
     * Query dictionary data information according to dictionary type and dictionary key value
     *
     * @param dictType  Dictionary type
     * @param dictValue Dictionary key
     * @return Dictionary tag
     */
    public String getLabel(String dictType, String dictValue) {
        return dictDataService.selectDictLabel(dictType, dictValue);
    }
}
