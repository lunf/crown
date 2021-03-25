package org.crown.project.system.user.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.poi.ExcelUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.model.ExcelDTO;
import org.crown.framework.responses.ApiResponses;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.TableData;
import org.crown.project.system.post.service.IPostService;
import org.crown.project.system.role.service.IRoleService;
import org.crown.project.system.user.domain.User;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * User Info
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends WebController<User> {

    private final String prefix = "system/user";

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPostService postService;

    @RequiresPermissions("system:user:view")
    @GetMapping
    public String user() {
        return prefix + "/user";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableData<User> list(User user) {
        startPage();
        List<User> list = userService.selectUserList(user);
        return getTableData(list);
    }

    @Log(title = "User Management", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public ExcelDTO export(User user) {
        List<User> list = userService.selectUserList(user);
        ExcelUtils<User> util = new ExcelUtils<>(User.class);
        return new ExcelDTO(util.exportExcel(list, "User data"));
    }

    @Log(title = "User Management", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @ResponseBody
    public ApiResponses<Void> importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtils<User> util = new ExcelUtils<>(User.class);
        List<User> userList = util.importExcel(file.getInputStream());
        String message = userService.importUser(userList, updateSupport);
        return success().setMsg(message);
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public ExcelDTO importTemplate() {
        ExcelUtils<User> util = new ExcelUtils<>(User.class);
        return new ExcelDTO(util.importTemplateExcel("User data"));
    }

    /**
     * New users
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("roles", roleService.selectRoleAll());
        mmap.put("posts", postService.list());
        return prefix + "/add";
    }

    /**
     * Add save user
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "User Management", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated User user) {
        ApiAssert.isFalse(ErrorCodeEnum.USER_CANNOT_UPDATE_SUPER_ADMIN, User.isAdmin(user.getUserId()));
        ApiAssert.isTrue(ErrorCodeEnum.USER_ACCOUNT_EXIST.overrideMsg(String.format("Login account [%s] already exists", user.getLoginName())), userService.checkLoginNameUnique(user.getLoginName()));
        ApiAssert.isTrue(ErrorCodeEnum.USER_PHONE_EXIST.overrideMsg(String.format("Mobile phone number [%s] already exists", user.getPhonenumber())), userService.checkPhoneUnique(user));
        ApiAssert.isTrue(ErrorCodeEnum.USER_EMAIL_EXIST.overrideMsg(String.format("The mailbox [%s] already exists", user.getEmail())), userService.checkEmailUnique(user));
        userService.insertUser(user);
    }

    /**
     * Modify user
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        mmap.put("posts", postService.selectAllPostsByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * Modify save user
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "User Management", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated User user) {
        ApiAssert.isFalse(ErrorCodeEnum.USER_CANNOT_UPDATE_SUPER_ADMIN, User.isAdmin(user.getUserId()));
        ApiAssert.isTrue(ErrorCodeEnum.USER_PHONE_EXIST.overrideMsg(String.format("Mobile phone number [%s] already exists", user.getPhonenumber())), userService.checkPhoneUnique(user));
        ApiAssert.isTrue(ErrorCodeEnum.USER_EMAIL_EXIST.overrideMsg(String.format("The mailbox [%s] already exists", user.getEmail())), userService.checkEmailUnique(user));
        userService.updateUser(user);
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "reset Password", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "reset Password", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public void resetPwd(User user) {
        if (userService.resetUserPwd(user)) {
            if (ShiroUtils.getUserId().equals(user.getUserId())) {
                ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
            }
        }
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "User Management", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {
        userService.deleteUserByIds(ids);
    }

    /**
     * Verify user name
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public boolean checkLoginNameUnique(User user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * Check mobile phone number
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public boolean checkPhoneUnique(User user) {
        return userService.checkPhoneUnique(user);
    }

    /**
     * Verify email address
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public boolean checkEmailUnique(User user) {
        return userService.checkEmailUnique(user);
    }

    /**
     * User status modification
     */
    @Log(title = "User Management", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:user:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public void changeStatus(User user) {
        userService.changeStatus(user);
    }
}