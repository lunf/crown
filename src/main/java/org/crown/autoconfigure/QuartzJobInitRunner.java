package org.crown.autoconfigure;

import org.crown.project.monitor.quartz.common.QuartzManage;
import org.crown.project.monitor.quartz.domain.Job;
import org.crown.project.monitor.quartz.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Timing task initialization
 *
 * @author Caratacus
 */
@Component
@Slf4j
public class QuartzJobInitRunner implements ApplicationRunner {

    @Autowired
    private IJobService jobService;

    @Autowired
    private QuartzManage quartzManage;

    /**
     * Reactivate enabled scheduled tasks when the project starts
     *
     * @param applicationArguments
     */
    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("Quartz timing task-loading data");
        jobService.query().eq(Job::getPaused, false).list().forEach(quartzJob -> quartzManage.addJob(quartzJob));
        log.info("Quartz timing task-data loading completed");
    }
}
