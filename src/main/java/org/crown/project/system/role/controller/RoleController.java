package org.crown.project.system.role.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.poi.ExcelUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.model.ExcelDTO;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.TableData;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.role.service.IRoleService;
import org.crown.project.system.user.domain.User;
import org.crown.project.system.user.domain.UserRole;
import org.crown.project.system.user.service.IUserService;
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
 * Role information
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends WebController {

    private final String prefix = "system/role";

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @RequiresPermissions("system:role:view")
    @GetMapping
    public String role() {
        return prefix + "/role";
    }

    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public TableData<Role> list(Role role) {
        startPage();
        List<Role> list = roleService.selectRoleList(role);
        return getTableData(list);
    }

    @Log(title = "Role management", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:role:export")
    @PostMapping("/export")
    @ResponseBody
    public ExcelDTO export(Role role) {
        List<Role> list = roleService.selectRoleList(role);
        ExcelUtils<Role> util = new ExcelUtils<>(Role.class);
        return new ExcelDTO(util.exportExcel(list, "Role data"));

    }

    /**
     * New role
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * Add save role
     */
    @RequiresPermissions("system:role:add")
    @Log(title = "Role management", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated Role role) {
        ApiAssert.isTrue(ErrorCodeEnum.ROLE_NAME_EXIST.overrideMsg("Role Name[" + role.getRoleName() + "]exists"), roleService.checkRoleNameUnique(role));
        ApiAssert.isTrue(ErrorCodeEnum.ROLE_KEY_EXIST.overrideMsg("Role Permissions[" + role.getRoleKey() + "]exists"), roleService.checkRoleKeyUnique(role));
        roleService.insertRole(role);
    }

    /**
     * Modify role
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/edit";
    }

    /**
     * Modify save role
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "Role management", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated Role role) {
        ApiAssert.isTrue(ErrorCodeEnum.ROLE_NAME_EXIST.overrideMsg("Role Name[" + role.getRoleName() + "]exists"), roleService.checkRoleNameUnique(role));
        ApiAssert.isTrue(ErrorCodeEnum.ROLE_KEY_EXIST.overrideMsg("Role Permissions[" + role.getRoleKey() + "]exists"), roleService.checkRoleKeyUnique(role));
        roleService.updateRole(role);
    }

    /**
     * Role assignment data permissions
     */
    @GetMapping("/authDataScope/{roleId}")
    public String authDataScope(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/dataScope";
    }

    /**
     * Save role assignment data permissions
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "Role management", businessType = BusinessType.UPDATE)
    @PostMapping("/authDataScope")
    @ResponseBody
    public void authDataScopeSave(Role role) {
        roleService.authDataScope(role);
        setSysUser(userService.selectUserById(getSysUser().getUserId()));
    }

    @RequiresPermissions("system:role:remove")
    @Log(title = "Role management", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {
        roleService.deleteRoleByIds(ids);
    }

    /**
     * Verify role name
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public boolean checkRoleNameUnique(Role role) {
        return roleService.checkRoleNameUnique(role);
    }

    /**
     * Verify role permissions
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public boolean checkRoleKeyUnique(Role role) {
        return roleService.checkRoleKeyUnique(role);
    }

    /**
     * Select menu tree
     */
    @GetMapping("/selectMenuTree")
    public String selectMenuTree() {
        return prefix + "/tree";
    }

    /**
     * Role status modification
     */
    @Log(title = "Role management", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:role:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public void changeStatus(Role role) {
        roleService.changeStatus(role);
    }

    /**
     * Assign users
     */
    @RequiresPermissions("system:role:edit")
    @GetMapping("/authUser/{roleId}")
    public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/authUser";
    }

    /**
     * Query the list of assigned user roles
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public TableData<User> allocatedList(User user) {
        startPage();
        List<User> list = userService.selectAllocatedList(user);
        return getTableData(list);
    }

    /**
     * Cancel authorization
     */
    @Log(title = "Role management", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancel")
    @ResponseBody
    public void cancelAuthUser(UserRole userRole) {
        roleService.deleteAuthUser(userRole);
    }

    /**
     * Cancel authorization in bulk
     */
    @Log(title = "Role management", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancelAll")
    @ResponseBody
    public void cancelAuthUserAll(Long roleId, String userIds) {
        roleService.deleteAuthUsers(roleId, userIds);
    }

    /**
     * Select user
     */
    @GetMapping("/authUser/selectUser/{roleId}")
    public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/selectUser";
    }

    /**
     * Query the list of unassigned user roles
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public TableData<User> unallocatedList(User user) {
        startPage();
        List<User> list = userService.selectUnallocatedList(user);
        return getTableData(list);
    }

    /**
     * Batch selection of user authorization
     */
    @Log(title = "Role management", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/selectAll")
    @ResponseBody
    public void selectAuthUserAll(Long roleId, String userIds) {
        roleService.insertAuthUsers(roleId, userIds);
    }
}