package org.crown.project.monitor.quartz.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.monitor.quartz.domain.Job;

/**
 * <p>
 * Timed task service class
 * </p>
 *
 * @author Caratacus
 */
public interface IJobService extends BaseService<Job> {

    /**
     * create
     *
     * @param resources
     * @return
     */
    Job create(Job resources);

    /**
     * update
     *
     * @param resources
     * @return
     */
    void update(Job resources);

    /**
     * del
     *
     * @param quartzJob
     */
    void delete(Job quartzJob);

    /**
     * Change the status of a scheduled task
     *
     * @param quartzJob
     */
    void updatePaused(Job quartzJob);

    /**
     * Execute scheduled tasks immediately
     *
     * @param quartzJob
     */
    void execute(Job quartzJob);

    /**
     * Query the list of scheduled tasks
     *
     * @param job
     * @return
     */
    List<Job> selectJobList(Job job);
}
