package org.crown.project.tool.gen.util;

import java.util.Arrays;

import org.crown.common.cons.CommonMap;
import org.crown.common.utils.Crowns;
import org.crown.common.utils.StringUtils;
import org.crown.project.tool.gen.GenConstants;
import org.crown.project.tool.gen.domain.GenTable;
import org.crown.project.tool.gen.domain.GenTableColumn;

/**
 * Code generator tools
 *
 * @author Crown
 */
public class GenUtils {

    /**
     * Initialize table information
     */
    public static void initTable(GenTable genTable, String operName) {
        String packagePath = Crowns.getGenerator().getPackagePath();
        String businessName = getBusinessName(genTable.getTableName());
        genTable.setClassName(StringUtils.convertToCamelCase(genTable.getTableName()));
        genTable.setPackageName(packagePath + "." + businessName);
        genTable.setModuleName(getModuleName(packagePath));
        genTable.setBusinessName(businessName);
        genTable.setFunctionName(replaceText(genTable.getTableComment()));
        genTable.setFunctionAuthor(Crowns.getGenerator().getAuthor());
        genTable.setCreateBy(operName);
    }

    /**
     * Initialize column attribute fields
     */
    public static void initColumnField(GenTableColumn column, GenTable table) {
        String dataType = getDbType(column.getColumnType());
        String columnName = column.getColumnName();
        column.setTableId(table.getTableId());
        column.setCreateBy(table.getCreateBy());
        // Set java field name
        column.setJavaField(StringUtils.toCamelCase(columnName));

        if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType)) {
            column.setJavaType(GenConstants.TYPE_STRING);
            // Set the string length more than 500 as a text field
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 ? GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
            column.setJavaType(GenConstants.TYPE_DATE);
            column.setHtmlType(GenConstants.HTML_DATETIME);
        } else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
            column.setHtmlType(GenConstants.HTML_INPUT);

            // If it is a floating point
            String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                column.setJavaType(GenConstants.TYPE_DOUBLE);
            }
            // If it is plastic surgery
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                column.setJavaType(GenConstants.TYPE_INTEGER);
            }
            // Long plastic
            else {
                column.setJavaType(GenConstants.TYPE_LONG);
            }
        } else {
            column.setJavaType(CommonMap.javaTypeMap.get(dataType));
        }

        // Insert fields (all fields need to be inserted by default)
        column.setIsInsert(GenConstants.REQUIRE);

        // Edit field
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.isPk()) {
            column.setIsEdit(GenConstants.REQUIRE);
        }
        // List field
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPk()) {
            column.setIsList(GenConstants.REQUIRE);
        }
        // Query field
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
            column.setIsQuery(GenConstants.REQUIRE);
        }

        // Query field type
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            column.setQueryType(GenConstants.QUERY_LIKE);
        }
        // Status field setting radio button
        if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
            column.setHtmlType(GenConstants.HTML_RADIO);
        }
        // Type & gender field setting drop-down box
        else if (StringUtils.endsWithIgnoreCase(columnName, "type")
                || StringUtils.endsWithIgnoreCase(columnName, "sex")) {
            column.setHtmlType(GenConstants.HTML_SELECT);
        }
    }

    /**
     * Check whether the array contains the specified value
     *
     * @param arr         Array
     * @param targetValue value
     * @return Does it contain
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * Get module name
     *
     * @param packageName Package names
     * @return Module name
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return StringUtils.substring(packageName, lastIndex + 1, nameLength);
    }

    /**
     * Get business name
     *
     * @param tableName Table Name
     * @return Business name
     */
    public static String getBusinessName(String tableName) {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StringUtils.substring(tableName, lastIndex + 1, nameLength);
    }

    /**
     * Keyword substitution
     *
     * @param text The name that needs to be replaced
     * @return Replaced name
     */
    public static String replaceText(String text) {
        return text.replaceAll("(?:表)", "");
    }

    /**
     * Get database type field
     *
     * @param columnType Column type
     * @return Column type after interception
     */
    public static String getDbType(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            return StringUtils.substringBefore(columnType, "(");
        } else {
            return columnType;
        }
    }

    /**
     * Get field length
     *
     * @param columnType Column type
     * @return Column type after interception
     */
    public static Integer getColumnLength(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }
}