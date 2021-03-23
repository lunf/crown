package org.crown.project.monitor.quartz.domain;

import java.util.Date;

import org.crown.common.annotation.Excel;
import org.crown.framework.web.domain.BaseQueryParams;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Scheduled task log
 * </p>
 *
 * @author Caratacus
 */
@Setter
@Getter
public class JobLog extends BaseQueryParams {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "Log sequence number")
    @TableId
    private Long jobLogId;
    /**
     * Execution class name
     */
    @Excel(name = "Execution class name")
    @ApiModelProperty(notes = "Execution class name")
    private String className;
    @ApiModelProperty(notes = "Time created")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * Cron expression
     */
    @Excel(name = "Cron expression")
    @ApiModelProperty(notes = "Cron expression")
    private String cron;
    /**
     * Exception information
     */
    @Excel(name = "Exception information")
    @ApiModelProperty(notes = "Exception information")
    private String exception;
    /**
     * Execution status (1 success 0 failure)
     */
    @Excel(name = "Execution status", readConverterExp = "1=success, 0=failure")
    @ApiModelProperty(notes = "Execution status")
    private Integer status;
    /**
     * job name
     */
    @Excel(name = "job name")
    @ApiModelProperty(notes = "job name")
    private String jobName;
    /**
     * parameter
     */
    @Excel(name = "parameter")
    @ApiModelProperty(notes = "parameter")
    private JSONObject jobParams;
    /**
     * Running time
     */
    @Excel(name = "Running time")
    @ApiModelProperty(notes = "Running time")
    private String runTime;

}
