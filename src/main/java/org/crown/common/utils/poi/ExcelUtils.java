package org.crown.common.utils.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.crown.common.annotation.Excel;
import org.crown.common.annotation.Excel.Type;
import org.crown.common.annotation.Excels;
import org.crown.common.utils.Crowns;
import org.crown.common.utils.DateUtils;
import org.crown.common.utils.StringUtils;
import org.crown.common.utils.converter.Convert;
import org.crown.common.utils.reflect.ReflectUtils;
import org.crown.framework.exception.Crown2Exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Excel related processing
 *
 * @author Crown
 */
@Slf4j
public class ExcelUtils<T> {

    /**
     * Maximum number of rows in Excel sheet, 65536 by default
     */
    public static final int sheetSize = 65536;

    /**
     * Worksheet name
     */
    private String sheetName;

    /**
     * Export type (EXPORT: export data; IMPORT: import template)
     */
    private Type type;

    /**
     * Workbook object
     */
    private Workbook wb;

    /**
     * Worksheet object
     */
    private Sheet sheet;

    /**
     * Import and export data list
     */
    private List<T> list;

    /**
     * Annotation list
     */
    private List<Object[]> fields;

    /**
     * Entity object
     */
    public final Class<T> clazz;

    public ExcelUtils(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void init(List<T> list, String sheetName, Type type) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        createExcelField();
        createWorkbook();
    }

    /**
     * The default first index name for excel forms is converted to list
     *
     * @param is Input stream
     * @return Collection after conversion
     */
    public List<T> importExcel(InputStream is) throws Exception {
        return importExcel(StringUtils.EMPTY, is);
    }

    /**
     * Convert the specified table index name to the excel form into a list
     *
     * @param sheetName Table index name
     * @param is        Input stream
     * @return Collection after conversion
     */
    public List<T> importExcel(String sheetName, InputStream is) throws Exception {
        this.type = Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList<>();
        Sheet sheet;
        if (StringUtils.isNotEmpty(sheetName)) {
            // If you specify the sheet name, take the content in the specified sheet.
            sheet = wb.getSheet(sheetName);
        } else {
            // If the passed sheet name does not exist, it will point to the first sheet by default.
            sheet = wb.getSheetAt(0);
        }

        if (sheet == null) {
            throw new IOException("File sheet does not exist");
        }

        int rows = sheet.getPhysicalNumberOfRows();

        if (rows > 0) {
            // Define a map to store the serial number and field of the excel column.
            Map<String, Integer> cellMap = new HashMap<>();
            // Get header
            Row heard = sheet.getRow(0);
            for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
                String value = this.getCellValue(heard, i).toString();
                cellMap.put(value, i);
            }
            // Process only when there is data, get all the fields of the class.
            Field[] allFields = clazz.getDeclaredFields();
            // Define a map to store the serial number and field of the column.
            Map<Integer, Field> fieldsMap = new HashMap<>();
            for (Field field : allFields) {
                Excel attr = field.getAnnotation(Excel.class);
                if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
                    // Set the private field properties of the class to be accessible.
                    field.setAccessible(true);
                    Integer column = cellMap.get(attr.name());
                    fieldsMap.put(column, field);
                }
            }
            for (int i = 1; i < rows; i++) {
                // Start to fetch data from the second row, the default first row is the header.
                Row row = sheet.getRow(i);
                T entity = null;
                for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet()) {
                    Object val = this.getCellValue(row, entry.getKey());

                    // If there is no instance, create a new one.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // Get the field of the corresponding column from the map.
                    Field field = fieldsMap.get(entry.getKey());
                    //Get the type and set the value according to the object type.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType) {
                        String s = Convert.toStr(val);
                        if (StringUtils.endsWith(s, ".0")) {
                            val = StringUtils.substringBefore(s, ".0");
                        } else {
                            val = Convert.toStr(val);
                        }
                    } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                        val = Convert.toInt(val);
                    } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                        val = Convert.toLong(val);
                    } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                        val = Convert.toDouble(val);
                    } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                        val = Convert.toFloat(val);
                    } else if (BigDecimal.class == fieldType) {
                        val = Convert.toBigDecimal(val);
                    } else if (Date.class == fieldType) {
                        if (val instanceof String) {
                            val = DateUtils.parseDate(val);
                        } else if (val instanceof Double) {
                            val = DateUtil.getJavaDate((Double) val);
                        }
                    }
                    if (StringUtils.isNotNull(fieldType)) {
                        Excel attr = field.getAnnotation(Excel.class);
                        String propertyName = field.getName();
                        if (StringUtils.isNotEmpty(attr.targetAttr())) {
                            propertyName = field.getName() + "." + attr.targetAttr();
                        } else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
                            val = reverseByExp(String.valueOf(val), attr.readConverterExp());
                        }
                        ReflectUtils.invokeSetter(entity, propertyName, val);
                    }
                }
                list.add(entity);
            }
        }
        return list;
    }

    /**
     * Import the data in the list data source into the excel form
     *
     * @param list      Export data collection
     * @param sheetName The name of the worksheet
     * @return result
     */
    public String exportExcel(List<T> list, String sheetName) {
        this.init(list, sheetName, Type.EXPORT);
        return exportExcel();
    }

    /**
     * Import the data in the list data source into the excel form
     *
     * @param sheetName The name of the worksheet
     * @return result
     */
    public String importTemplateExcel(String sheetName) {
        this.init(null, sheetName, Type.IMPORT);
        return exportExcel();
    }

    /**
     * Import the data in the list data source into the excel form
     *
     * @return result
     */
    public String exportExcel() {
        OutputStream out = null;
        try {
            // How many sheets are taken out.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                createSheet(sheetNo, index);

                // Produce a line
                Row row = sheet.createRow(0);
                int column = 0;
                // Write the column header name of each field
                for (Object[] os : fields) {
                    Excel excel = (Excel) os[1];
                    this.createCell(excel, row, column++);
                }
                if (Type.EXPORT.equals(type)) {
                    fillExcelData(index);
                }
            }
            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return filename;
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "导出Excel失败，请联系网站管理员！");
        } finally {
            IOUtils.closeQuietly(wb, out);
        }
    }

    /**
     * Fill in excel data
     *
     * @param index Serial number
     */
    public void fillExcelData(int index) {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, list.size());
        // Write each record, each record corresponds to a row in the excel table
        CellStyle cs = wb.createCellStyle();
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        for (int i = startNo; i < endNo; i++) {
            Row row = sheet.createRow(i + 1 - startNo);
            // Get the exported object.
            T vo = list.get(i);
            int column = 0;
            for (Object[] os : fields) {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                // Set the private properties of the entity class to be accessible
                field.setAccessible(true);
                this.addCell(excel, row, vo, field, column++, cs);
            }
        }
    }

    /**
     * Create cell
     */
    public Cell createCell(Excel attr, Row row, int column) {
        // Create column
        Cell cell = row.createCell(column);
        // Write column name
        cell.setCellValue(attr.name());
        CellStyle cellStyle = createStyle(attr, row, column);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * Create table style
     */
    public CellStyle createStyle(Excel attr, Row row, int column) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if (attr.name().contains("Note：")) {
            Font font = wb.createFont();
            font.setColor(HSSFFont.COLOR_RED);
            cellStyle.setFont(font);
            cellStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
            sheet.setColumnWidth(column, 6000);
        } else {
            Font font = wb.createFont();
            // Bold
            font.setBold(true);
            // Choose the font format you need to use
            cellStyle.setFont(font);
            cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_YELLOW.getIndex());
            // Set column width
            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
            row.setHeight((short) (attr.height() * 20));
        }
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        // If the prompt information is set, the mouse will be placed on the prompt.
        if (StringUtils.isNotEmpty(attr.prompt())) {
            // There are 2-101 columns of prompts by default.
            setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
        }
        // If the combo attribute is set, this column can only be selected but not entered
        if (attr.combo().length > 0) {
            // By default, columns 2-101 can only be selected and cannot be entered.
            setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
        }
        return cellStyle;
    }

    /**
     * Add cell
     */
    public Cell addCell(Excel attr, Row row, T vo, Field field, int column, CellStyle cs) {
        Cell cell = null;
        try {
            // Set row height
            row.setHeight((short) (attr.height() * 20));
            // Decide whether or not to export according to the settings in Excel. In some cases, you need to keep it blank, and users are expected to fill in this column.
            if (attr.isExport()) {
                // Create cell
                cell = row.createCell(column);
                cell.setCellStyle(cs);

                // Used to read the attributes in the object
                Object value = getTargetValue(vo, field, attr);
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) value));
                } else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(convertByExp(String.valueOf(value), readConverterExp));
                } else {
                    // Fill in if the data exists, and fill in the blank if it does not exist.
                    cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
                }
            }
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to export Excel to add cells", e);
        }
        return cell;
    }

    /**
     * Set POI XSSFSheet cell tips
     *
     * @param sheet         Form
     * @param promptTitle   Prompt title
     * @param promptContent Prompt content
     * @param firstRow      Start line
     * @param endRow        End line
     * @param firstCol      Start column
     * @param endCol        End column
     */
    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
                              int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    /**
     * To set the value of some columns, only numeric data can be entered, and a drop-down box is displayed.
     *
     * @param sheet    The sheet to be set.
     * @param textlist The content displayed in the drop-down box
     * @param firstRow Start line
     * @param endRow   End line
     * @param firstCol Start column
     * @param endCol   End column
     * @return 设置好的sheet.
     */
    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // Load drop-down list content
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        // Set the cell on which the data validity is loaded, the four parameters are: start row, end row, start column, end column
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // Data validity object
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        // Dealing with Excel compatibility issues
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }

        sheet.addValidationData(dataValidation);
    }

    /**
     * Analyze the derived value 0=male, 1=female, 2=unknown
     *
     * @param propertyValue Parameter value
     * @param converterExp  Translation notes
     * @return Parsed value
     */
    public static String convertByExp(String propertyValue, String converterExp) {
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (itemArray[0].equals(propertyValue)) {
                return itemArray[1];
            }
        }
        return propertyValue;
    }

    /**
     * Reverse analytic value male=0, female=1, unknown=2
     *
     * @param propertyValue Parameter value
     * @param converterExp  Translation notes
     * @return Parsed value
     */
    public static String reverseByExp(String propertyValue, String converterExp) {
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (itemArray[1].equals(propertyValue)) {
                return itemArray[0];
            }
        }
        return propertyValue;
    }

    /**
     * Encoding file name
     */
    public String encodingFilename(String filename) {
        return filename + "_" + UUID.randomUUID().toString() + ".xlsx";
    }

    /**
     * Get download path
     *
     * @param filename file name
     */
    public String getAbsoluteFile(String filename) {
        String downloadPath = Crowns.getDownloadPath(filename);
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

    /**
     * Get the attribute value in the bean
     *
     * @param vo    Entity object
     * @param field Field
     * @param excel annotation
     * @return Final attribute value
     * @throws Exception
     */
    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.contains(".")) {
                String[] targets = target.split("[.]");
                for (String name : targets) {
                    o = getValue(o, name);
                }
            } else {
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * Get the value in the form of the get method method of the attribute of the class
     *
     * @param o
     * @param name
     * @return value
     * @throws Exception
     */
    private Object getValue(Object o, String name) throws Exception {
        if (StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = clazz.getMethod(methodName);
            o = method.invoke(o);
        }
        return o;
    }

    /**
     * Get all defined fields
     */
    private void createExcelField() {
        this.fields = new ArrayList<>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Field field : tempFields) {
            // 单注解
            if (field.isAnnotationPresent(Excel.class)) {
                putToField(field, field.getAnnotation(Excel.class));
            }

            // 多注解
            if (field.isAnnotationPresent(Excels.class)) {
                Excels attrs = field.getAnnotation(Excels.class);
                Excel[] excels = attrs.value();
                for (Excel excel : excels) {
                    putToField(field, excel);
                }
            }
        }
    }

    /**
     * Put it in the field collection
     */
    private void putToField(Field field, Excel attr) {
        if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
            this.fields.add(new Object[]{field, attr});
        }
    }

    /**
     * Create a workbook
     */
    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
    }

    /**
     * Create worksheet
     *
     * @param sheetNo Number of sheets
     * @param index   Serial number
     */
    public void createSheet(double sheetNo, int index) {
        this.sheet = wb.createSheet();
        // 设置工作表的名称.
        if (sheetNo == 0) {
            wb.setSheetName(index, sheetName);
        } else {
            wb.setSheetName(index, sheetName + index);
        }
    }

    /**
     * Get cell value
     *
     * @param row    Fetched row
     * @param column Get the cell column number
     * @return Cell value
     */
    public Object getCellValue(Row row, int column) {
        if (row == null) {
            return null;
        }
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                    val = cell.getNumericCellValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        val = DateUtil.getJavaDate((Double) val); // POI Excel 日期格式转换
                    } else {
                        if ((Double) val % 1 > 0) {
                            val = new DecimalFormat("0.00").format(val);
                        } else {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                } else if (cell.getCellType() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellType() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }

            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }
}