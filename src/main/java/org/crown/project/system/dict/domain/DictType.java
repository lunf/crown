package org.crown.project.system.dict.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.crown.common.annotation.Excel;
import org.crown.framework.web.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

/**
 * Dictionary type object sys_dict_type
 *
 * @author Crown
 */
@Setter
@Getter
public class DictType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Dictionary primary key
     */
    @Excel(name = "Dictionary primary key")
    @TableId
    private Long dictId;

    /**
     * Dictionary name
     */
    @Excel(name = "Dictionary name")
    @NotBlank(message = "Dictionary name cannot be empty")
    @Size(max = 100, message = "The length of the dictionary type name cannot exceed 100 characters")
    private String dictName;

    /**
     * Dictionary type
     */
    @Excel(name = "Dictionary type")
    @NotBlank(message = "The dictionary type cannot be empty")
    @Size(max = 100, message = "The length of the dictionary type cannot exceed 100 characters")
    private String dictType;

    /**
     * Status (0 normal, 1 disabled)
     */
    @Excel(name = "status", readConverterExp = "0=normal, 1=disabled")
    private String status;

    /**
     * Remarks
     */
    private String remark;

}
