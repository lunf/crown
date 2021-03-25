package org.crown.project.tool.gen;

/**
 * Code generation general constants
 *
 * @author Crown
 */
public class GenConstants {

    /**
     * Single table (addition, deletion, modification and check)
     */
    public static final String TPL_CRUD = "crud";

    /**
     * Tree table (addition, deletion, modification, query)
     */
    public static final String TPL_TREE = "tree";

    /**
     * Tree encoding field
     */
    public static final String TREE_CODE = "treeCode";

    /**
     * Tree parent encoding field
     */
    public static final String TREE_PARENT_CODE = "treeParentCode";

    /**
     * Tree name field
     */
    public static final String TREE_NAME = "treeName";

    /**
     * Database string type
     */
    public static final String[] COLUMNTYPE_STR = {"char", "varchar", "narchar", "varchar2", "tinytext", "text",
            "mediumtext", "longtext"};

    /**
     * Database time type
     */
    public static final String[] COLUMNTYPE_TIME = {"datetime", "time", "date", "timestamp"};

    /**
     * Database number type
     */
    public static final String[] COLUMNTYPE_NUMBER = {"tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bigint", "float", "float", "double", "decimal"};

    /**
     * No need to edit fields on the page
     */
    public static final String[] COLUMNNAME_NOT_EDIT = {"id", "create_by", "create_time", "deleted"};

    /**
     * List fields that do not need to be displayed on the page
     */
    public static final String[] COLUMNNAME_NOT_LIST = {"id", "create_by", "create_time", "deleted", "update_by",
            "update_time"};

    /**
     * The page does not require query fields
     */
    public static final String[] COLUMNNAME_NOT_QUERY = {"id", "create_by", "create_time", "deleted", "update_by",
            "update_time", "remark"};

    /**
     * Text box
     */
    public static final String HTML_INPUT = "input";

    /**
     * Text field
     */
    public static final String HTML_TEXTAREA = "textarea";

    /**
     * Drop down box
     */
    public static final String HTML_SELECT = "select";

    /**
     * Single box
     */
    public static final String HTML_RADIO = "radio";

    /**
     * Checkbox
     */
    public static final String HTML_CHECKBOX = "checkbox";

    /**
     * Date control
     */
    public static final String HTML_DATETIME = "datetime";

    /**
     * String type
     */
    public static final String TYPE_STRING = "String";

    /**
     * Integer
     */
    public static final String TYPE_INTEGER = "Integer";

    /**
     * Long integer
     */
    public static final String TYPE_LONG = "Long";

    /**
     * Floating point
     */
    public static final String TYPE_DOUBLE = "Double";

    /**
     * High-precision calculation type
     */
    public static final String TYPE_BIGDECIMAL = "BigDecimal";

    /**
     * Time type
     */
    public static final String TYPE_DATE = "Date";

    /**
     * Fuzzy query
     */
    public static final String QUERY_LIKE = "LIKE";

    /**
     * need
     */
    public static final String REQUIRE = "1";
}
