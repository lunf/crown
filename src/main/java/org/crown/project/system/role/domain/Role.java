package org.crown.project.system.role.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.crown.common.annotation.Excel;
import org.crown.framework.web.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Getter;
import lombok.Setter;

/**
 * Role table sys_role
 *
 * @author Crown
 */
@Setter
@Getter
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Role ID
     */
    @Excel(name = "Role number")
    @TableId
    private Long roleId;

    /**
     * Role Name
     */
    @Excel(name = "Role Name")
    @NotBlank(message = "Role name cannot be empty")
    @Size(max = 30, message = "The length of the role name cannot exceed 30 characters")
    private String roleName;

    /**
     * Role Permissions
     */
    @Excel(name = "Role Permissions")
    @NotBlank(message = "The permission character cannot be empty")
    @Size(max = 100, message = "The permission character length cannot exceed 100 characters")
    private String roleKey;

    /**
     * Role ranking
     */
    @Excel(name = "Role ranking")
    @NotBlank(message = "Display order cannot be empty")
    private String roleSort;

    /**
     * Data scope (1: all data permissions; 2: custom data permissions; 3: data permissions for this department; 4: data permissions for this department and below)
     */
    @Excel(name = "data range", readConverterExp = "1=All data permissions, 2=Custom data permissions, 3=Data permissions in this department, 4=Data permissions in this department and below")
    private String dataScope;

    /**
     * Role status (0 normal, 1 disabled)
     */
    @Excel(name = "Role status", readConverterExp = "0=normal, 1=disabled")
    private String status;

    /**
     * Delete flag (0 existing, 1 deleted)
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleted;

    /**
     * Remarks
     */
    private String remark;

    /**
     * Does the user exist? This role ID does not exist by default
     */
    @TableField(exist = false)
    private boolean flag = false;

    /**
     * Menu group
     */
    @TableField(exist = false)
    private Long[] menuIds;

    /**
     * Department Group (Data Authority)
     */
    @TableField(exist = false)
    private Long[] deptIds;

}
