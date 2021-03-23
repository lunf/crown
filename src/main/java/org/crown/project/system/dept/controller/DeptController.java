package org.crown.project.system.dept.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.StringUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.domain.Ztree;
import org.crown.project.system.dept.domain.Dept;
import org.crown.project.system.dept.service.IDeptService;
import org.crown.project.system.role.domain.Role;
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
 * Department Information
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends WebController<Dept> {

    private final String prefix = "system/dept";

    @Autowired
    private IDeptService deptService;

    @RequiresPermissions("system:dept:view")
    @GetMapping
    public String dept() {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Dept> list(Dept dept) {
        return deptService.selectDeptList(dept);
    }

    /**
     * New department
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        mmap.put("dept", deptService.selectDeptById(parentId));
        return prefix + "/add";
    }

    /**
     * Add saving department
     */
    @Log(title = "Department management", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated Dept dept) {
        ApiAssert.isTrue(ErrorCodeEnum.DEPT_NAME_EXIST.overrideMsg("Department name[" + dept.getDeptName() + "]already exists"), deptService.checkDeptNameUnique(dept));
        deptService.insertDept(dept);
    }

    /**
     * Modify
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        Dept dept = deptService.selectDeptById(deptId);
        if (StringUtils.isNotNull(dept) && 100L == deptId) {
            dept.setParentName("无");
        }
        mmap.put("dept", dept);
        return prefix + "/edit";
    }

    /**
     * Save
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated Dept dept) {
        ApiAssert.isTrue(ErrorCodeEnum.DEPT_NAME_EXIST.overrideMsg("Department name[" + dept.getDeptName() + "]already exists"), deptService.checkDeptNameUnique(dept));
        ApiAssert.isFalse(ErrorCodeEnum.DEPT_PARENT_DEPT_CANNOT_MYSELF, dept.getParentId().equals(dept.getDeptId()));
        deptService.updateDept(dept);
    }

    /**
     * Delete
     */
    @Log(title = "Department management", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dept:remove")
    @GetMapping("/remove/{deptId}")
    @ResponseBody
    public void remove(@PathVariable("deptId") Long deptId) {
        ApiAssert.isFalse(ErrorCodeEnum.DEPT_EXISTING_LOWER_LEVEL_DEPT, deptService.exist(Wrappers.<Dept>lambdaQuery().eq(Dept::getParentId, deptId)));
        ApiAssert.isFalse(ErrorCodeEnum.DEPT_EXISTING_USER, deptService.exist(Wrappers.<Dept>lambdaQuery().eq(Dept::getParentId, deptId)));
    }

    /**
     * Name of verification department
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public boolean checkDeptNameUnique(Dept dept) {
        return deptService.checkDeptNameUnique(dept);
    }

    /**
     * Select department tree
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        mmap.put("dept", deptService.selectDeptById(deptId));
        return prefix + "/tree";
    }

    /**
     * Load department list tree
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        return deptService.selectDeptTree(new Dept());
    }

    /**
     * Load the list tree of role departments (data permissions)
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Ztree> deptTreeData(Role role) {
        return deptService.roleDeptTreeData(role);
    }
}
