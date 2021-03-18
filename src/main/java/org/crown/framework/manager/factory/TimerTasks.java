package org.crown.framework.manager.factory;

import java.util.Date;
import java.util.TimerTask;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.cons.Constants;
import org.crown.common.utils.Crowns;
import org.crown.common.utils.IpUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.spring.ApplicationUtils;
import org.crown.framework.springboot.properties.Email;
import org.crown.project.monitor.exceLog.domain.ExceLog;
import org.crown.project.monitor.exceLog.service.IExceLogService;
import org.crown.project.monitor.logininfor.domain.Logininfor;
import org.crown.project.monitor.logininfor.service.ILogininforService;
import org.crown.project.monitor.online.domain.OnlineSession;
import org.crown.project.monitor.online.domain.UserOnline;
import org.crown.project.monitor.online.service.IUserOnlineService;
import org.crown.project.monitor.operlog.domain.OperLog;
import org.crown.project.monitor.operlog.service.IOperLogService;

import cn.hutool.extra.mail.MailUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

/**
 * TimerTask asynchronous tool class
 *
 * @author Caratacus
 */
@Slf4j
public class TimerTasks {

    /**
     * Synchronize session to database
     *
     * @param session Online user session
     * @return task
     */
    public static TimerTask syncSession(final OnlineSession session) {
        return new TimerTask() {
            @Override
            public void run() {
                UserOnline online = new UserOnline();
                online.setSessionId(String.valueOf(session.getId()));
                online.setDeptName(session.getDeptName());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLoginLocation(IpUtils.getRealAddress(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
                online.setStatus(session.getStatus());
                online.setSession(session);
                ApplicationUtils.getBean(IUserOnlineService.class).saveOrUpdate(online);

            }
        };
    }

    /**
     * Synchronize session to database
     *
     * @param ip      IP address
     * @param exceLog Exception log
     * @return Task
     */
    public static TimerTask saveExceLog(final String ip, final int status, final ExceLog exceLog) {
        return new TimerTask() {
            @Override
            public void run() {
                Email email = Crowns.getEmail();
                if (status >= HttpServletResponse.SC_INTERNAL_SERVER_ERROR && email.isEnabled()) {
                    MailUtil.send(email.getSend(), "Crown2 system abnormal warning", exceLog.getContent(), false);
                }
                exceLog.setIpAddr(IpUtils.getRealAddress(ip));
                ApplicationUtils.getBean(IExceLogService.class).save(exceLog);
            }
        };
    }

    /**
     * Operation log record
     *
     * @param operLog Operation log information
     * @return task
     */
    public static TimerTask recordOper(final OperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // Remote query operation location
                operLog.setOperLocation(IpUtils.getRealAddress(operLog.getOperIp()));
                operLog.setOperTime(new Date());
                ApplicationUtils.getBean(IOperLogService.class).save(operLog);
            }
        };
    }

    /**
     * Log login information
     *
     * @param username username
     * @param status   status
     * @param message  news
     * @param args     list
     * @return task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ApplicationUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroUtils.getIp();
        return new TimerTask() {
            @Override
            public void run() {
                String address = IpUtils.getRealAddress(ip);
                StringBuilder s = new StringBuilder();
                s.append(getBlock(ip));
                s.append(address);
                s.append(getBlock(username));
                s.append(getBlock(status));
                s.append(getBlock(message));
                // Print information to log
                log.info(s.toString(), args);
                // Get the client operating system
                String os = userAgent.getOperatingSystem().getName();
                // Get the client browser
                String browser = userAgent.getBrowser().getName();
                // Package object
                Logininfor logininfor = new Logininfor();
                logininfor.setLoginName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // Log status
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
                    logininfor.setStatus(Constants.SUCCESS);
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    logininfor.setStatus(Constants.FAIL);
                }
                logininfor.setLoginTime(new Date());
                // Insert data
                ApplicationUtils.getBean(ILogininforService.class).save(logininfor);
            }
        };
    }

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
