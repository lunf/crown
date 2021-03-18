package org.crown.framework.manager;

import javax.annotation.PreDestroy;

import org.crown.framework.shiro.web.session.SpringSessionValidationScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Ensure that the background thread can be closed when the application exits
 *
 * @author cj
 */
@Component
@Slf4j
public class ShutdownManager {

    @Autowired(required = false)
    private SpringSessionValidationScheduler springSessionValidationScheduler;

    @PreDestroy
    public void destroy() {
        shutdownSpringSessionValidationScheduler();
        shutdownAsyncManager();
    }

    /**
     * Stop Session session check
     */
    private void shutdownSpringSessionValidationScheduler() {
        if (springSessionValidationScheduler != null && springSessionValidationScheduler.isEnabled()) {
            try {
                log.info("Session session check-stopping");
                springSessionValidationScheduler.disableSessionValidation();
                log.info("Session session check-stopped");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Stop executing tasks asynchronously
     */
    private void shutdownAsyncManager() {
        try {
            log.info("============Close the background task thread pool============");
            ThreadExecutors.shutdown();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
