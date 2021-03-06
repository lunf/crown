package org.crown.project.system.dict.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.poi.ExcelUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.model.ExcelDTO;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.domain.Ztree;
import org.crown.framework.web.page.TableData;
import org.crown.project.system.dict.domain.DictType;
import org.crown.project.system.dict.service.IDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * Data dictionary information
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/dict")
public class DictTypeController extends WebController<DictType> {

    private final String prefix = "system/dict/type";

    @Autowired
    private IDictTypeService dictTypeService;

    @RequiresPermissions("system:dict:view")
    @GetMapping
    public String dictType() {
        return prefix + "/type";
    }

    @PostMapping("/list")
    @RequiresPermissions("system:dict:list")
    @ResponseBody
    public TableData<DictType> list(DictType dictType) {
        startPage();
        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
        return getTableData(list);
    }

    @Log(title = "Dictionary type", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    @ResponseBody
    public ExcelDTO export(DictType dictType) {
        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtils<DictType> util = new ExcelUtils<>(DictType.class);
        return new ExcelDTO(util.exportExcel(list, "????????????"));

    }

    /**
     * New dictionary type
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * Added save dictionary type
     */
    @Log(title = "Dictionary type", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dict:add")
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated DictType dict) {
        ApiAssert.isTrue(ErrorCodeEnum.DICT_TYPE_EXIST.overrideMsg("Dictionary type[" + dict.getDictType() + "]exists"), dictTypeService.checkDictTypeUnique(dict));
        dictTypeService.save(dict);
    }

    /**
     * Modify dictionary type
     */
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.getById(dictId));
        return prefix + "/edit";
    }

    /**
     * Modify the saved dictionary type
     */
    @Log(title = "Dictionary type", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dict:edit")
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated DictType dict) {
        ApiAssert.isTrue(ErrorCodeEnum.DICT_TYPE_EXIST.overrideMsg("Dictionary type[" + dict.getDictType() + "]exists"), dictTypeService.checkDictTypeUnique(dict));
        dictTypeService.updateDictType(dict);
    }

    @Log(title = "Dictionary type", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dict:remove")
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {
        dictTypeService.deleteDictTypeByIds(ids);
    }

    /**
     * View dictionary details
     */
    @RequiresPermissions("system:dict:list")
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.getById(dictId));
        mmap.put("dictList", dictTypeService.list());
        return "system/dict/data/data";
    }

    /**
     * Check dictionary type
     */
    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public boolean checkDictTypeUnique(DictType dictType) {
        return dictTypeService.checkDictTypeUnique(dictType);
    }

    /**
     * Select dictionary tree
     */
    @GetMapping("/selectDictTree/{columnId}/{dictType}")
    public String selectDeptTree(@PathVariable("columnId") Long columnId, @PathVariable("dictType") String dictType,
                                 ModelMap mmap) {
        mmap.put("columnId", columnId);
        mmap.put("dict", dictTypeService.getOne(Wrappers.<DictType>lambdaQuery().eq(DictType::getDictType, dictType)));
        return prefix + "/tree";
    }

    /**
     * Load dictionary list tree
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        return dictTypeService.selectDictTree(new DictType());
    }
}
