package org.crown.project.monitor.quartz.common;

import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.crown.common.cons.QuartzCons;
import org.crown.framework.exception.Crown2Exception;
import org.crown.project.monitor.quartz.domain.Job;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Quartz management tools
 *
 * @author Caratacus
 */
@Slf4j
@Component
public class QuartzManage {

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    public void addJob(Job quartzJob) {
        try {
            // Build job information
            JobDetail jobDetail = JobBuilder.newJob(QuartzExecutionJob.class).
                    withIdentity(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId()).build();

            //Create Trigger by trigger name and cron expression
            Trigger cronTrigger = newTrigger()
                    .withIdentity(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId())
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCron()))
                    .build();

            cronTrigger.getJobDataMap().put(QuartzCons.JOB_KEY_PREFIX, quartzJob);

            //Reset start time
            ((CronTriggerImpl) cronTrigger).setStartTime(new Date());

            //Perform scheduled tasks
            scheduler.scheduleJob(jobDetail, cronTrigger);

            // Pause task
            if (quartzJob.getPaused()) {
                pauseJob(quartzJob);
            }
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "????????????????????????", e);
        }
    }

    /**
     * Update job cron expression
     *
     * @param quartzJob
     * @throws SchedulerException
     */
    public void updateJobCron(Job quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // If it does not exist, create a scheduled task
            if (trigger == null) {
                addJob(quartzJob);
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCron());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //Reset start time
            ((CronTriggerImpl) trigger).setStartTime(new Date());
            trigger.getJobDataMap().put(QuartzCons.JOB_KEY_PREFIX, quartzJob);

            scheduler.rescheduleJob(triggerKey, trigger);
            // Pause task
            if (quartzJob.getPaused()) {
                pauseJob(quartzJob);
            }
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "????????????????????????", e);

        }

    }

    /**
     * ????????????job
     *
     * @param quartzJob
     * @throws SchedulerException
     */
    public void deleteJob(Job quartzJob) {
        try {
            JobKey jobKey = JobKey.jobKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "????????????????????????", e);

        }
    }

    /**
     * ????????????job
     *
     * @param quartzJob
     * @throws SchedulerException
     */
    public void resumeJob(Job quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // ??????????????????????????????????????????
            if (trigger == null)
                addJob(quartzJob);
            JobKey jobKey = JobKey.jobKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "????????????????????????", e);
        }
    }

    /**
     * ????????????job
     *
     * @param quartzJob
     * @throws SchedulerException
     */
    public void runAJobNow(Job quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // ??????????????????????????????????????????
            if (trigger == null) {
                addJob(quartzJob);
            }
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(QuartzCons.JOB_KEY_PREFIX, quartzJob);
            JobKey jobKey = JobKey.jobKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            scheduler.triggerJob(jobKey, dataMap);
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "????????????????????????", e);
        }
    }

    /**
     * ????????????job
     *
     * @param quartzJob
     * @throws SchedulerException
     */
    public void pauseJob(Job quartzJob) {
        try {
            JobKey jobKey = JobKey.jobKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "????????????????????????", e);
        }
    }
}
