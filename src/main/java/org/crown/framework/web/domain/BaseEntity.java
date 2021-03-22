package org.crown.framework.web.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity base class
 *
 * @author Crown
 */
@Setter
@Getter
public class BaseEntity extends BaseQueryParams {

    private static final long serialVersionUID = 1L;

    /**
     * creator
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * time created
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * modifier
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * time modified
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
