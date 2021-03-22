package org.crown.project.monitor.logininfor.domain;

import java.util.Date;

import org.crown.common.annotation.Excel;
import org.crown.framework.web.domain.BaseQueryParams;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

/**
 * System access log sys_logininfor
 *
 * @author Crown
 */
@Setter
@Getter
public class Logininfor extends BaseQueryParams {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "Serial number")
    @TableId
    private Long infoId;

    /**
     * user account
     */
    @Excel(name = "user account")
    private String loginName;

    /**
     * Login status 0 success 1 failure
     */
    @Excel(name = "Login status", readConverterExp = "1=success, 0=failure")
    private Integer status;

    /**
     * Login IP address
     */
    @Excel(name = "Login address")
    private String ipaddr;

    /**
     * Login location
     */
    @Excel(name = "Login location")
    private String loginLocation;

    /**
     * Browser type
     */
    @Excel(name = "Browser")
    private String browser;

    /**
     * operating system
     */
    @Excel(name = "operating system ")
    private String os;

    /**
     * Prompt message
     */
    @Excel(name = "Prompt message")
    private String msg;

    /**
     * interview time
     */
    @Excel(name = "Interview time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

}