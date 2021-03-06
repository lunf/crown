package org.crown.framework.shiro.web.session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.crown.common.utils.Threads;
import org.crown.framework.spring.ApplicationUtils;
import org.crown.framework.springboot.properties.ShiroProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Custom task scheduler completed
 *
 * @author Crown
 */
@Component
@Slf4j
public class SpringSessionValidationScheduler implements SessionValidationScheduler {

    /**
     * The timer is used to handle the timeout suspension request, and is also used to reconnect when the connection is disconnected.
     */
    @Autowired
    @Qualifier("scheduledExecutorService")
    private ScheduledExecutorService executorService;

    private volatile boolean enabled = false;

    /**
     * Session authentication manager
     */
    @Autowired
    @Qualifier("sessionManager")
    private ValidatingSessionManager sessionManager;

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Starts session validation by creating a spring PeriodicTrigger.
     */
    @Override
    public void enableSessionValidation() {

        int sessionValidationInterval = ApplicationUtils.getBean(ShiroProperties.class).getSession().getValidationInterval();

        enabled = true;

        if (log.isDebugEnabled()) {
            log.debug("Scheduling session validation job using Spring Scheduler with "
                    + "session validation interval of [" + sessionValidationInterval + "]ms...");
        }

        try {
            executorService.scheduleAtFixedRate(() -> {
                if (enabled) {
                    sessionManager.validateSessions();
                }
            }, 1000, sessionValidationInterval * 60 * 1000, TimeUnit.MILLISECONDS);

            this.enabled = true;

            if (log.isDebugEnabled()) {
                log.debug("Session validation job successfully scheduled with Spring Scheduler.");
            }

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Error starting the Spring Scheduler session validation job.  Session validation may not occur.", e);
            }
        }
    }

    @Override
    public void disableSessionValidation() {
        if (log.isDebugEnabled()) {
            log.debug("Stopping Spring Scheduler session validation job...");
        }

        if (this.enabled) {
            Threads.shutdownAndAwaitTermination(executorService);
        }
        this.enabled = false;
    }
}
