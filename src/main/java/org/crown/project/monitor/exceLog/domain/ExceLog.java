package org.crown.project.monitor.exceLog.domain;

import java.util.Date;

import org.crown.framework.web.domain.BaseQueryParams;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Getter;
import lombok.Setter;

/**
 * Exception log table sys_exce_log
 *
 * @author Caratacus
 */
@Setter
@Getter
public class ExceLog extends BaseQueryParams {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * Current operator
     */
    private String operName;
    /**
     * Request path
     */
    private String url;
    /**
     * Controller method
     */
    private String actionMethod;
    /**
     * Interface running time unit: ms
     */
    private String runTime;
    /**
     * IP address
     */
    private String ipAddr;
    /**
     * Log details
     */
    private String content;
    /**
     * Time created
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}
