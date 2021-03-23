package org.crown.project.monitor.quartz.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.StringUtils;
import org.crown.common.utils.poi.ExcelUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.model.ExcelDTO;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.TableData;
import org.crown.project.monitor.quartz.common.CronUtils;
import org.crown.project.monitor.quartz.domain.Job;
import org.crown.project.monitor.quartz.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * Timing task front controller
 * </p>
 *
 * @author Caratacus
 */
@Controller
@RequestMapping("/monitor/job")
public class JobController extends WebController<Job> {

    private final String prefix = "monitor/job";

    @Autowired
    private IJobService jobService;

    @RequiresPermissions("monitor:job:view")
    @GetMapping
    public String job() {
        return prefix + "/job";
    }

    @RequiresPermissions("monitor:job:list")
    @PostMapping("/list")
    @ResponseBody
    public TableData<Job> list(Job job) {
        startPage();
        List<Job> list = jobService.selectJobList(job);
        return getTableData(list);
    }

    @Log(title = "Timed task", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:job:export")
    @PostMapping("/export")
    @ResponseBody
    public ExcelDTO export(Job job) {
        List<Job> list = jobService.selectJobList(job);
        ExcelUtils<Job> util = new ExcelUtils<>(Job.class);
        return new ExcelDTO(util.exportExcel(list, "定时任务"));
    }

    @Log(title = "Timed task", businessType = BusinessType.DELETE)
    @RequiresPermissions("monitor:job:remove")
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {
        List<String> idVals = StringUtils.split2List(ids);
        idVals.forEach(id -> {
            Job job = jobService.getById(id);
            ApiAssert.notNull(ErrorCodeEnum.JOB_NOT_FOUND.overrideMsg("ID is not found[" + id + "]Timed tasks"), job);
            jobService.delete(job);
        });
    }

    @RequiresPermissions("monitor:job:detail")
    @GetMapping("/detail/{jobId}")
    public String detail(@PathVariable("jobId") Long jobId, ModelMap mmap) {
        mmap.put("name", "job");
        mmap.put("job", jobService.getById(jobId));
        return prefix + "/detail";
    }

    /**
     * Task scheduling status modification
     */
    @Log(title = "Timed task", businessType = BusinessType.UPDATE)
    @RequiresPermissions("monitor:job:changeStatus")
    @PostMapping("/changeStatus")
    @ResponseBody
    public void changeStatus(Job job) {
        Job newJob = jobService.getById(job.getJobId());
        ApiAssert.notNull(ErrorCodeEnum.JOB_NOT_FOUND.overrideMsg("ID not found[" + job.getJobId() + "]Timed tasks"), job);
        jobService.updatePaused(newJob);
    }

    /**
     * Task scheduling is executed once immediately
     */
    @Log(title = "Timed task", businessType = BusinessType.UPDATE)
    @RequiresPermissions("monitor:job:changeStatus")
    @PostMapping("/run")
    @ResponseBody
    public void run(Job job) {
        Job newJob = jobService.getById(job.getJobId());
        ApiAssert.notNull(ErrorCodeEnum.JOB_NOT_FOUND.overrideMsg("ID not found[" + job.getJobId() + "]Timed tasks"), job);
        jobService.execute(newJob);
    }

    /**
     * New schedule
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * Add save schedule
     */
    @Log(title = "Timed task", businessType = BusinessType.INSERT)
    @RequiresPermissions("monitor:job:add")
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated Job job) {
        jobService.create(job);
    }

    /**
     * Modify schedule
     */
    @GetMapping("/edit/{jobId}")
    public String edit(@PathVariable("jobId") Long jobId, ModelMap mmap) {
        mmap.put("job", jobService.getById(jobId));
        return prefix + "/edit";
    }

    /**
     * Modify save schedule
     */
    @Log(title = "Timed task", businessType = BusinessType.UPDATE)
    @RequiresPermissions("monitor:job:edit")
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated Job job) {
        jobService.update(job);
    }

    /**
     * Verify that the cron expression is valid
     */
    @PostMapping("/checkCronExpressionIsValid")
    @ResponseBody
    public boolean checkCronExpressionIsValid(Job job) {
        return CronUtils.isValid(job.getCron());
    }
}
