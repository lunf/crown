package org.crown.framework.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.crown.common.utils.Threads;
import org.crown.framework.spring.ApplicationUtils;

/**
 * Thread asynchronous execution tool class
 *
 * @author Caratacus
 */
public abstract class ThreadExecutors {

    /**
     * Operation delay 10 milliseconds
     */
    private static final int OPERATE_DELAY_TIME = 10;

    /**
     * Perform task
     *
     * @param task task
     */
    public static void execute(TimerTask task) {
        getExecutorService().schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * Obtain ScheduledExecutorService
     *
     * @return
     */
    public static ScheduledExecutorService getExecutorService() {
        return ApplicationUtils.getBean(ScheduledExecutorService.class);
    }

    /**
     * Stop task thread pool
     */
    public static void shutdown() {
        Threads.shutdownAndAwaitTermination(getExecutorService());
    }
}
