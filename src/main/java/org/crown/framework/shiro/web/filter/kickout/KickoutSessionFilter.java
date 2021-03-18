package org.crown.framework.shiro.web.filter.kickout;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.crown.common.cons.ShiroConstants;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.utils.RequestUtils;
import org.crown.framework.utils.ResponseUtils;
import org.crown.project.system.user.domain.User;

/**
 * Login account control filter
 *
 * @author Crown
 */
public class KickoutSessionFilter extends AccessControlFilter {

    /**
     * Maximum number of sessions for the same user
     **/
    private int maxSession = -1;

    /**
     * Kick out the user who was logged in before/when logged in. Default false Kick out the user who was logged in before
     **/
    private Boolean kickoutAfter = false;

    /**
     * Address after kicked out
     **/
    private String kickoutUrl;

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered() || maxSession == -1) {
            // If there is no login or the maximum number of user sessions is -1, proceed directly to the following process
            return true;
        }
        try {
            Session session = subject.getSession();
            // Currently logged in user
            User user = ShiroUtils.getSysUser();
            String loginName = user.getLoginName();
            Serializable sessionId = session.getId();

            // Read the cache user and save it if not
            Deque<Serializable> deque = cache.get(loginName);
            if (deque == null) {
                // Initialize the queue
                deque = new ArrayDeque<>();
            }

            // If there is no such sessionId in the queue and the user has not been kicked out; put it in the queue
            if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
                // Store the sessionId in the queue
                deque.push(sessionId);
                // Cache the user's sessionId queue
                cache.put(loginName, deque);
            }

            // If the number of sessionIds in the queue exceeds the maximum number of sessions, the exceeding user(s) will be kicked out
            while (deque.size() > maxSession) {
                Serializable kickoutSessionId;
                // Whether to kick out the users who log in later, the default is false; that is, the user who logs in after kicks out the user who logs in before;
                if (kickoutAfter) {
                    // Kick out the latter
                    kickoutSessionId = deque.removeFirst();
                } else {
                    // Kick out the former
                    kickoutSessionId = deque.removeLast();
                }
                // Update the cache queue after kicking out
                cache.put(loginName, deque);

                try {
                    // Get the session object of the kicked sessionId
                    Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                    if (null != kickoutSession) {
                        // Set the kickout attribute of the session to indicate a kickout
                        kickoutSession.setAttribute("kickout", true);
                    }
                } catch (Exception e) {
                    // If listed as exception, the user will not be kicked out
                }
            }

            // If kicked out, (the former or the latter) exits and is redirected to the address afterwards
            if (session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout")) {
                // sign out
                subject.logout();
                saveRequest(request);
                sendLogOutResponse(request, response);
                return false;
            }
            return true;
        } catch (Exception e) {
            sendLogOutResponse(request, response);
            return false;
        }
    }

    /**
     * Send re-login response
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void sendLogOutResponse(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (RequestUtils.isAjaxRequest(req)) {
            ResponseUtils.sendFail(req, res, ErrorCodeEnum.USER_ELSEWHERE_LOGIN);
        } else {
            WebUtils.issueRedirect(request, response, kickoutUrl);
        }
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // Set the prefix of the Cache key
    public void setCacheManager(CacheManager cacheManager) {
        // Must be the same as the cache name in the ehcache cache configuration
        this.cache = cacheManager.getCache(ShiroConstants.SYS_USERCACHE);
    }
}
