package org.crown.framework.shiro.service;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.cons.Constants;
import org.crown.common.cons.ShiroConstants;
import org.crown.common.cons.UserConstants;
import org.crown.common.enums.UserStatus;
import org.crown.common.utils.DateUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.exception.Crown2Exception;
import org.crown.framework.manager.ThreadExecutors;
import org.crown.framework.manager.factory.TimerTasks;
import org.crown.framework.spring.ApplicationUtils;
import org.crown.project.system.user.domain.User;
import org.crown.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Login verification method
 *
 * @author Crown
 */
@Component
public class LoginService {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private IUserService userService;

    /**
     * log in
     */
    public User login(String username, String password) {
        // Verification code verification
        if (!StringUtils.isEmpty(ApplicationUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "验证码错误"));
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "验证码错误");
        }
        // Username or password is empty error
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名或密码为空"));
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "用户名或密码为空");
        }
        // If the password is not in the specified range, error
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名或密码错误"));
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "用户名或密码错误");
        }

        // Username is not in the specified range error
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名或密码错误"));
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "用户名或密码错误");
        }

        // Query user information
        User user = userService.selectUserByLoginName(username);

        if (user == null && maybeMobilePhoneNumber(username)) {
            user = userService.selectUserByPhoneNumber(username);
        }

        if (user == null && maybeEmail(username)) {
            user = userService.selectUserByEmail(username);
        }

        if (user == null) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名或密码错误"));
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "用户名或密码错误");
        }

        if (user.getDeleted()) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除"));
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "对不起，您的账号已被删除");
        }

        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "用户已被禁用，请联系管理员", user.getRemark()));
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "用户已被禁用，请联系管理员");
        }

        passwordService.validate(user, password);

        ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功"));
        recordLoginInfo(user);
        return user;
    }

    private boolean maybeEmail(String username) {
        return username.matches(UserConstants.EMAIL_PATTERN);
    }

    private boolean maybeMobilePhoneNumber(String username) {
        return username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN);
    }

    /**
     * Log login information
     */
    public void recordLoginInfo(User user) {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateById(user);
    }
}
