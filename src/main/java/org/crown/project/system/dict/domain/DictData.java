package org.crown.project.system.dict.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.crown.common.annotation.Excel;
import org.crown.common.cons.UserConstants;
import org.crown.framework.web.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

/**
 * Dictionary data table sys_dict_data
 *
 * @author Crown
 */
@Setter
@Getter
public class DictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Dictionary encoding
     */
    @Excel(name = "Dictionary encoding")
    @TableId
    private Long dictCode;

    /**
     * Dictionary sort
     */
    @Excel(name = "Dictionary sort")
    private Long dictSort;

    /**
     * Dictionary tag
     */
    @Excel(name = "Dictionary tag")
    @NotBlank(message = "Dictionary tag cannot be empty")
    @Size(max = 100, message = "The length of the dictionary tag cannot exceed 100 characters")
    private String dictLabel;

    /**
     * Dictionary key
     */
    @Excel(name = "Dictionary key")
    @NotBlank(message = "Dictionary key value cannot be empty")
    @Size(max = 100, message = "The length of dictionary keys cannot exceed 100 characters")
    private String dictValue;

    /**
     * Dictionary type
     */
    @Excel(name = "Dictionary type")
    @NotBlank(message = "The dictionary type cannot be empty")
    @Size(max = 100, message = "The length of the dictionary type cannot exceed 100 characters")
    private String dictType;

    /**
     * Dictionary style
     */
    @Excel(name = "Dictionary style")
    @Size(max = 100, message = "The style attribute length cannot exceed 100 characters")
    private String cssClass;

    /**
     * Table dictionary style
     */
    private String listClass;

    /**
     * Whether the default (Y yes, N no)
     */
    @Excel(name = "Default", readConverterExp = "Y=Yes, N=No")
    private String isDefault;

    /**
     * Status (0 normal, 1 disabled)
     */
    @Excel(name = "status", readConverterExp = "0=normal, 1=disabled")
    private String status;

    /**
     * Remarks
     */
    private String remark;

    public boolean getDefault() {
        return UserConstants.YES.equals(this.isDefault);
    }
}
