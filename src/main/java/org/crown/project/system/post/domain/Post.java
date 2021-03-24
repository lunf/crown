package org.crown.project.system.post.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.crown.common.annotation.Excel;
import org.crown.framework.web.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

/**
 * Job list sys_post
 *
 * @author Crown
 */
@Setter
@Getter
public class Post extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Position number
     */
    @Excel(name = "Position number")
    @TableId
    private Long postId;

    /**
     * Post code
     */
    @Excel(name = "Post code")
    @NotBlank(message = "Post code cannot be empty")
    @Size(max = 64, message = "The length of the post code cannot exceed 64 characters")
    private String postCode;

    /**
     * Position Title
     */
    @Excel(name = "Position Title")
    @NotBlank(message = "The post name cannot be empty")
    @Size(max = 50, message = "The length of the post name cannot exceed 50 characters")
    private String postName;

    /**
     * Job sorting
     */
    @Excel(name = "Job sorting")
    @NotBlank(message = "Display order cannot be empty")
    private String postSort;

    /**
     * Status (0 normal, 1 disabled)
     */
    @Excel(name = "status", readConverterExp = "0=normal, 1=disabled")
    private String status;

    /**
     * Remarks
     */
    private String remark;

    /**
     * Does the user exist? This post ID does not exist by default
     */
    @TableField(exist = false)
    private boolean flag = false;

}
