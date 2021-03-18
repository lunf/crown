package org.crown.framework.shiro.session;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.crown.common.utils.IpUtils;
import org.crown.common.utils.StringUtils;
import org.crown.framework.spring.ApplicationUtils;
import org.crown.project.monitor.online.domain.OnlineSession;
import org.crown.project.monitor.online.domain.UserOnline;
import org.springframework.stereotype.Component;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * Custom sessionFactory session
 *
 * @author Crown
 */
@Component
public class OnlineSessionFactory implements SessionFactory {

    public Session createSession(UserOnline userOnline) {
        OnlineSession onlineSession = userOnline.getSession();
        if (StringUtils.isNotNull(onlineSession) && onlineSession.getId() == null) {
            onlineSession.setId(userOnline.getSessionId());
        }
        return userOnline.getSession();
    }

    @Override
    public Session createSession(SessionContext initData) {
        OnlineSession session = new OnlineSession();
        if (initData instanceof WebSessionContext) {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            if (request != null) {
                UserAgent userAgent = UserAgent.parseUserAgentString(ApplicationUtils.getRequest().getHeader("User-Agent"));
                // Get the client operating system
                String os = userAgent.getOperatingSystem().getName();
                // Get the client browser
                String browser = userAgent.getBrowser().getName();
                session.setHost(IpUtils.getIpAddr(request));
                session.setBrowser(browser);
                session.setOs(os);
            }
        }
        return session;
    }
}
