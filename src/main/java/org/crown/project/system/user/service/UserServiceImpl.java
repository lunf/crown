package org.crown.project.system.user.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.annotation.DataScope;
import org.crown.common.utils.StringUtils;
import org.crown.common.utils.TypeUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.exception.Crown2Exception;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.shiro.service.PasswordService;
import org.crown.framework.utils.ApiAssert;
import org.crown.project.system.config.service.IConfigService;
import org.crown.project.system.post.domain.Post;
import org.crown.project.system.post.service.IPostService;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.role.service.IRoleService;
import org.crown.project.system.user.domain.User;
import org.crown.project.system.user.domain.UserPost;
import org.crown.project.system.user.domain.UserRole;
import org.crown.project.system.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

/**
 * User business layer processing
 *
 * @author Crown
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserPostService userPostService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IConfigService configService;

    @Autowired
    private PasswordService passwordService;

    @Override
    @DataScope
    public List<User> selectUserList(User user) {
        // Generate data permission filter conditions
        return baseMapper.selectUserList(user);
    }

    /**
     * Query the list of assigned user roles based on conditions
     *
     * @param user User Info
     * @return User information collection information
     */
    @DataScope
    public List<User> selectAllocatedList(User user) {
        return baseMapper.selectAllocatedList(user);
    }

    /**
     * Query the list of unassigned user roles based on conditions
     *
     * @param user User Info
     * @return User information collection information
     */
    @DataScope
    public List<User> selectUnallocatedList(User user) {
        return baseMapper.selectUnallocatedList(user);
    }

    @Override
    public User selectUserByLoginName(String userName) {
        return baseMapper.selectUserByLoginName(userName);
    }

    @Override
    public User selectUserByPhoneNumber(String phoneNumber) {
        return baseMapper.selectUserByPhoneNumber(phoneNumber);
    }

    @Override
    public User selectUserByEmail(String email) {
        return baseMapper.selectUserByEmail(email);
    }

    @Override
    public User selectUserById(Long userId) {
        return baseMapper.selectUserById(userId);
    }

    @Override
    public boolean deleteUserByIds(String ids) {
        List<Long> userIds = StringUtils.split2List(ids, TypeUtils::castToLong);
        for (Long userId : userIds) {
            ApiAssert.isFalse(ErrorCodeEnum.USER_CANNOT_UPDATE_SUPER_ADMIN, User.isAdmin(userId));
        }
        return delete().in(User::getUserId, userIds).execute();
    }

    @Override
    @Transactional
    public boolean insertUser(User user) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        // Add user information
        save(user);
        // New user post association
        insertUserPost(user);
        // New user and role management
        insertUserRole(user);
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        Long userId = user.getUserId();
        user.setUpdateBy(ShiroUtils.getLoginName());
        // Delete user and role association
        userRoleService.delete().eq(UserRole::getUserId, userId).execute();
        // New user and role management
        insertUserRole(user);
        // Delete user and post association
        userPostService.delete().eq(UserPost::getUserId, userId).execute();
        // New user and position management
        insertUserPost(user);
        return updateById(user);
    }

    @Override
    public boolean resetUserPwd(User user) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return updateById(user);
    }

    /**
     * New user role information
     *
     * @param user User object
     */
    public void insertUserRole(User user) {
        userRoleService.saveBatch(
                Arrays.stream(user.getRoleIds()).map(roleId -> {
                    UserRole ur = new UserRole();
                    ur.setUserId(user.getUserId());
                    ur.setRoleId(roleId);
                    return ur;
                }).collect(Collectors.toList())
        );
    }

    /**
     * New user post information
     *
     * @param user User object
     */
    public void insertUserPost(User user) {
        userPostService.saveBatch(
                Arrays.stream(user.getPostIds()).map(postId -> {
                    UserPost up = new UserPost();
                    up.setUserId(user.getUserId());
                    up.setPostId(postId);
                    return up;
                }).collect(Collectors.toList())
        );
    }

    @Override
    public boolean checkLoginNameUnique(String loginName) {
        return query().eq(User::getLoginName, loginName).nonExist();
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        Long userId = user.getUserId();
        User info = query().select(User::getUserId, User::getPhonenumber).eq(User::getPhonenumber, user.getPhonenumber()).getOne();
        return Objects.isNull(info) || info.getUserId().equals(userId);
    }

    @Override
    public boolean checkEmailUnique(User user) {
        Long userId = user.getUserId();
        User info = query().select(User::getUserId, User::getEmail).eq(User::getEmail, user.getEmail()).getOne();
        return Objects.isNull(info) || info.getUserId().equals(userId);
    }

    @Override
    public String selectUserRoleGroup(Long userId) {
        List<Role> list = roleService.selectRolesByUserId(userId);
        StringBuilder idsStr = new StringBuilder();
        for (Role role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String selectUserPostGroup(Long userId) {
        List<Post> list = postService.selectPostsByUserId(userId);
        StringBuilder idsStr = new StringBuilder();
        for (Post post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String importUser(List<User> userList, Boolean isUpdateSupport) {
        if (CollectionUtils.isEmpty(userList)) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "?????????????????????????????????");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        String password = configService.getConfigValueByKey("sys.user.initPassword");
        for (User user : userList) {
            try {
                // ??????????????????????????????
                User u = baseMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(password);
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("????????? ").append(user.getLoginName()).append(" ????????????");
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("????????? ").append(user.getLoginName()).append(" ????????????");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("????????? ").append(user.getLoginName()).append(" ?????????");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "????????? " + user.getLoginName() + " ???????????????";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "?????????????????????????????? " + failureNum + " ??????????????????????????????????????????");
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, failureMsg.toString());
        } else {
            successMsg.insert(0, "????????????????????????????????????????????? " + successNum + " ?????????????????????");
        }
        return successMsg.toString();
    }

    @Override
    public boolean changeStatus(User user) {
        ApiAssert.isFalse(ErrorCodeEnum.USER_CANNOT_UPDATE_SUPER_ADMIN, User.isAdmin(user.getUserId()));
        return updateById(user);
    }
}
