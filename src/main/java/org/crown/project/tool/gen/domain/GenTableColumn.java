package org.crown.project.tool.gen.domain;

import javax.validation.constraints.NotBlank;

import org.crown.common.utils.StringUtils;
import org.crown.framework.web.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * Code generation business field table gen_table_column
 *
 * @author Crown
 */
@Setter
@Getter
@TableName("gen_table_column")
public class GenTableColumn extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Column ID
     */
    @TableId
    private Long columnId;

    /**
     * Table ID
     */
    private Long tableId;

    /**
     * Column name
     */
    private String columnName;

    /**
     * Column description
     */
    private String columnComment;

    /**
     * Column type
     */
    private String columnType;

    /**
     * JAV type
     */
    private String javaType;

    /**
     * JAVA field name
     */
    @NotBlank(message = "Java properties cannot be empty")
    private String javaField;

    /**
     * Whether the primary key (1 yes)
     */
    private String isPk;

    /**
     * Whether it is self-increasing (1 yes)
     */
    private String isIncrement;

    /**
     * Is it required (1 yes)
     */
    private String isRequired;

    /**
     * Is it an insert field（1 yes）
     */
    private String isInsert;

    /**
     * Whether to edit the field（1 yes）
     */
    private String isEdit;

    /**
     * List field（1 yes）
     */
    private String isList;

    /**
     * Whether to query the field（1 yes）
     */
    private String isQuery;

    /**
     * Query method (EQ is equal to, NE is not equal to, GT is greater than, LT is less than, LIKE fuzzy, BETWEEN range)
     */
    private String queryType;

    /**
     * Display type (input text box, textarea text field, select drop-down box, checkbox checkbox, radio single selection box, datetime date control)
     */
    private String htmlType;

    /**
     * Dictionary type
     */
    private String dictType;

    /**
     * Sort
     */
    private Integer sort;

    public boolean isPk() {
        return isPk(this.isPk);
    }

    public boolean isPk(String isPk) {
        return isPk != null && StringUtils.equals("1", isPk);
    }

    public boolean isIncrement(String isIncrement) {
        return isIncrement != null && StringUtils.equals("1", isIncrement);
    }

    public boolean isRequired(String isRequired) {
        return isRequired != null && StringUtils.equals("1", isRequired);
    }

    public boolean isInsert(String isInsert) {
        return isInsert != null && StringUtils.equals("1", isInsert);
    }

    public boolean isEdit(String isEdit) {
        return isEdit != null && StringUtils.equals("1", isEdit);
    }

    public boolean isList() {
        return isList(this.isList);
    }

    public boolean isList(String isList) {
        return isList != null && StringUtils.equals("1", isList);
    }

    public boolean isQuery(String isQuery) {
        return isQuery != null && StringUtils.equals("1", isQuery);
    }

    public boolean isSuperColumn() {
        return isSuperColumn(this.javaField);
    }

    public static boolean isSuperColumn(String javaField) {
        return StringUtils.equalsAnyIgnoreCase(javaField, "createBy", "createTime", "updateBy", "updateTime");
    }

    public String readConverterExp() {
        String remarks = StringUtils.substringBetween(this.columnComment, "（", "）");
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(remarks)) {
            for (String value : remarks.split(" ")) {
                if (StringUtils.isNotEmpty(value)) {
                    Object startStr = value.subSequence(0, 1);
                    String endStr = value.substring(1);
                    sb.append(startStr).append("=").append(endStr).append(",");
                }
            }
            return sb.deleteCharAt(sb.length() - 1).toString();
        } else {
            return this.columnComment;
        }
    }
}