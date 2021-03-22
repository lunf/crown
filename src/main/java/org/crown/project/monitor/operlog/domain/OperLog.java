package org.crown.project.monitor.operlog.domain;

import java.util.Date;
import java.util.List;

import org.crown.common.annotation.Excel;
import org.crown.framework.web.domain.BaseQueryParams;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

/**
 * Operation log record table oper_log
 *
 * @author Crown
 */
@Setter
@Getter
public class OperLog extends BaseQueryParams {

    private static final long serialVersionUID = 1L;

    /**
     * Log primary key
     */
    @Excel(name = "Operation number")
    @TableId
    private Long operId;

    /**
     * Operation module
     */
    @Excel(name = "Operation module")
    private String title;

    /**
     * Business type
     */
    @Excel(name = "Business type", readConverterExp = "0=others, 1=new, 2=modify, 3=delete, 4=authorize, 5=export, 6=import, 7=retire, 8=generate code, 9=clear data")
    private Integer businessType;

    /**
     * Business type array
     */
    @TableField(exist = false)
    private List<Integer> businessTypes;

    /**
     * Request method
     */
    @Excel(name = "Request method")
    private String method;

    /**
     * Operator category
     */
    @Excel(name = "Operation category", readConverterExp = "0=others, 1=background user, 2=mobile terminal user")
    private Integer operatorType;

    /**
     * operator
     */
    @Excel(name = "operator")
    private String operName;

    /**
     * Department name
     */
    @Excel(name = "Department name")
    private String deptName;

    /**
     * Request url
     */
    @Excel(name = "Request address")
    private String operUrl;

    /**
     * Operation address
     */
    @Excel(name = "Operation address")
    private String operIp;

    /**
     * Operating location
     */
    @Excel(name = "Operating location")
    private String operLocation;

    /**
     * Request parameter
     */
    @Excel(name = "Request parameter")
    private String operParam;

    /**
     * State 0 normal 1 abnormal
     */
    @Excel(name = "status", readConverterExp = "1=normal, 0=abnormal")
    private Integer status;

    /**
     * wrong information
     */
    @Excel(name = "wrong information")
    private String errorMsg;

    /**
     * Operating time
     */
    @Excel(name = "Operating time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

}
