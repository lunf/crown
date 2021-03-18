package org.crown.framework.shiro.realm;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.shiro.service.LoginService;
import org.crown.project.system.menu.service.IMenuService;
import org.crown.project.system.role.service.IRoleService;
import org.crown.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Customize Realm to handle login permissions
 *
 * @author Crown
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private LoginService loginService;

    /**
     * Authorization
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        User user = ShiroUtils.getSysUser();
        // Role list
        Set<String> roles;
        // function list
        Set<String> menus;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // Administrator has all permissions
        if (user.isAdmin()) {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        } else {
            roles = roleService.selectRoleKeys(user.getUserId());
            menus = menuService.selectPermsByUserId(user.getUserId());
            // The role is added to the AuthorizationInfo authentication object
            info.setRoles(roles);
            // Add authorization to AuthorizationInfo authentication object
            info.setStringPermissions(menus);
        }
        return info;
    }

    /**
     * Login authentication
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        User user = loginService.login(username, password);
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * Clear cache permissions
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
