package org.crown.project.monitor.quartz.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.monitor.quartz.domain.JobLog;

/**
 * <p>
 * Scheduled task log service class
 * </p>
 *
 * @author Caratacus
 */
public interface IJobLogService extends BaseService<JobLog> {

    /**
     * Query the log list of scheduled tasks
     *
     * @param jobLog
     * @return
     */
    List<JobLog> selectJobLogList(JobLog jobLog);
}
