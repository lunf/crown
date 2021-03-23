package org.crown.project.system.config.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.crown.common.annotation.Excel;
import org.crown.framework.web.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

/**
 * Parameter configuration table sys_config
 *
 * @author Crown
 */
@Setter
@Getter
public class Config extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Parameter primary key
     */
    @Excel(name = "Parameter primary key")
    @TableId
    private Long configId;

    /**
     * Parameter name
     */
    @Excel(name = "parameter name")
    @NotBlank(message = "The parameter name cannot be empty")
    @Size(max = 100, message = "The parameter name cannot exceed 100 characters")
    private String configName;

    /**
     * Parameter key name
     */
    @Excel(name = "Parameter key name")
    @NotBlank(message = "The length of the parameter key name cannot be empty")
    @Size(max = 100, message = "The length of the parameter key name cannot exceed 100 characters")
    private String configKey;

    /**
     * Parameter key value
     */
    @Excel(name = "Parameter key value")
    @NotBlank(message = "The parameter key value cannot be empty")
    @Size(max = 500, message = "The length of the parameter key value cannot exceed 500 characters")
    private String configValue;

    /**
     * Built-in system (Y Yes, N No)
     */
    @Excel(name = "Built-in system", readConverterExp = "Y=Yes, N=No")
    private String configType;

    /**
     * Remarks
     */
    private String remark;
}
