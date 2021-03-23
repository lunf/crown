package org.crown.project.monitor.quartz.domain;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.crown.common.annotation.Excel;
import org.crown.common.utils.StringUtils;
import org.crown.framework.spring.validator.Cron;
import org.crown.framework.web.domain.BaseEntity;
import org.crown.project.monitor.quartz.common.CronUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Timed task
 * </p>
 *
 * @author Caratacus
 */
@Setter
@Getter
public class Job extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Task ID
     */
    @Excel(name = "Task number")
    @TableId
    private Long jobId;
    /**
     * job name
     */
    @Excel(name = "job name")
    @ApiModelProperty(notes = "job name")
    @NotBlank(message = "The task name cannot be empty")
    @Size(max = 64, message = "The task name cannot exceed 64 characters")
    private String jobName;
    /**
     * Execution class name
     */
    @Excel(name = "Execution class name")
    @ApiModelProperty(notes = "Execution class name")
    @NotBlank(message = "Execution class name cannot be empty")
    @Size(max = 255, message = "Execution class name cannot exceed 255 characters")
    private String className;
    /**
     * parameter
     */
    @Excel(name = "parameter")
    @ApiModelProperty(notes = "parameter")
    private JSONObject jobParams;
    /**
     * Cron expression
     */
    @Excel(name = "Cron expression")
    @ApiModelProperty(notes = "Cron expression")
    @NotBlank(message = "Cron execution expression cannot be empty")
    @Cron(message = "Cron execution expression is incorrect")
    private String cron;
    /**
     * Task status
     */
    @Excel(name = "Task status", readConverterExp = "true = pause, false = running")
    @ApiModelProperty(notes = "Task status")
    private Boolean paused;
    /**
     * Remarks
     */
    @Excel(name = "Remarks")
    @ApiModelProperty(notes = "Remarks")
    private String remark;
    /**
     * Delete flag (0 remaining, 1 deleted）
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleted;

    /**
     * Get the next execution time
     *
     * @return
     */
    public Date getNextValidTime() {
        String cron = getCron();
        if (StringUtils.isNotEmpty(cron)) {
            return CronUtils.getNextExecution(cron);
        }
        return null;
    }
}
