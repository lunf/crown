package org.crown.project.tool.gen.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.crown.common.utils.StringUtils;
import org.crown.framework.web.domain.BaseEntity;
import org.crown.project.tool.gen.GenConstants;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * Business table gen_table
 *
 * @author Crown
 */
@Setter
@Getter
@TableName("gen_table")
public class GenTable extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * serial number
     */
    @TableId
    private Long tableId;

    /**
     * Table name
     */
    @NotBlank(message = "Table name cannot be empty")
    private String tableName;

    /**
     * Table description
     */
    @NotBlank(message = "Table description cannot be empty")
    private String tableComment;

    /**
     * 实Body class name (first letter capitalized)
     */
    @NotBlank(message = "The entity class name cannot be empty")
    private String className;

    /**
     * Template used (crud single table operation tree tree table operation)
     */
    private String tplCategory;

    /**
     * Generate package path
     */
    @NotBlank(message = "The generated package path cannot be empty")
    private String packageName;

    /**
     * Generate module name
     */
    @NotBlank(message = "The generated module name cannot be empty")
    private String moduleName;

    /**
     * Generate business name
     */
    @NotBlank(message = "The generated business name cannot be empty")
    private String businessName;

    /**
     * Generate function name
     */
    @NotBlank(message = "Generated function name cannot be empty")
    private String functionName;

    /**
     * Generate Author
     */
    @NotBlank(message = "Author cannot be empty")
    private String functionAuthor;

    /**
     * Primary key information
     */
    @TableField(exist = false)
    private GenTableColumn pkColumn;

    /**
     * Listed information
     */
    @Valid
    @TableField(exist = false)
    private List<GenTableColumn> columns;

    /**
     * Other build options
     */
    private String options;

    /**
     * Tree encoding field
     */
    @TableField(exist = false)
    private String treeCode;

    /**
     * Tree parent encoding field
     */
    @TableField(exist = false)
    private String treeParentCode;

    /**
     * Tree name field
     */
    @TableField(exist = false)
    private String treeName;

    /**
     * Remarks
     */
    private String remark;

    public static boolean isTree(String tplCategory) {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
    }

    public static boolean isCrud(String tplCategory) {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_CRUD, tplCategory);
    }
}