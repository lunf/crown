package org.crown.project.system.dept.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.crown.framework.web.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Getter;
import lombok.Setter;

/**
 * Department table sys_dept
 *
 * @author Crown
 */
@Setter
@Getter
public class Dept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Department ID
     */
    @TableId
    private Long deptId;

    /**
     * Parent department ID
     */
    private Long parentId;

    /**
     * Ancestor list
     */
    private String ancestors;

    /**
     * Department name
     */
    @NotBlank(message = "Department name cannot be empty")
    @Size(max = 30, message = "Department name cannot exceed 30 characters")
    private String deptName;

    /**
     * display order
     */
    @NotBlank(message = "Display order cannot be empty")
    private String orderNum;

    /**
     * Principal
     */
    private String leader;

    /**
     * Phone number
     */
    @Size(max = 11, message = "The length of phone number cannot exceed 11 characters")
    private String phone;

    /**
     * Email address
     */
    @Email(message = "Email format is incorrect")
    @Size(max = 50, message = "The length of the address cannot exceed 50 characters")
    private String email;

    /**
     * Department status: 0 normal, 1 disabled
     */
    private String status;

    /**
     * Delete flag (0 remaining, 1 deleted)
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleted;

    /**
     * Parent department name
     */
    @TableField(exist = false)
    private String parentName;

}
