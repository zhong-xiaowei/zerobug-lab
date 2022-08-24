//package cn.com.zerobug.demo.excel.excel;
//
//import org.apache.poi.ss.usermodel.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
///**
// * @author zhongxiaowei
// * @contact zhongxiaowei.nice@gmail.com
// * @date 2022/7/31
// */
//public class ExcelReader<T> extends ExcelHandler<T> {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReader.class);
//
//    private InputStream inputStream;
//    private ExcelSheet excelSheet;
//    private int headerRows;
//
//    public ExcelReader(Class<T> classType) {
//        super(classType, READ_MODE);
//    }
//
//    public ExcelReader<T> from(InputStream is) {
//        try {
//            this.inputStream = is;
//            setWorkbook(WorkbookFactory.create(is));
//        } catch (Exception e) {
//            LOGGER.error("创建workbook失败，原因:{0}", e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//        return this;
//    }
//
//    public ExcelReader<T> sheet(int headerRows) {
//        sheet(null, headerRows);
//        return this;
//    }
//
//    public ExcelReader<T> sheet(String sheetName, int headerRows) {
//        Sheet sheet = null;
//        if (StringUtils.hasText(sheetName)) {
//            sheet = getWorkbook().getSheet(sheetName);
//        } else {
//            sheet = getWorkbook().getSheetAt(0);
//        }
//        Assert.notNull(sheet, "未能读取到sheet");
//        this.headerRows = headerRows;
//        return this;
//    }
//
//    public ExcelReader<T> parseFields() {
////        this.fields = getClassType().getDeclaredFields();
////        for (int i = 0; i < fields.length; i++) {
////            Field field = fields[i];
////            field.setAccessible(true);
////            ExcelCell annotation = field.getAnnotation(ExcelCell.class);
////            if (annotation != null) {
////                String name = annotation.name();
////                // 初始化 字段对应的标题头索引
////                fieldAliasIndexMap.put(name, i);
////            }
////        }
//        return this;
//    }
//
//    public List<T> doRead() throws Exception {
//
//
////        Sheet sheet = excelSheet.getSheet();
////        int totalRows = sheet.getLastRowNum();
////        if (totalRows > 0) {
////            FormulaEvaluator formulaEvaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
////            buildFieldIndex(sheet, 1, formulaEvaluator);
////            List<T> result = new ArrayList<>(sheet.getLastRowNum() - rowPointer);
////            for (; rowPointer <= totalRows; rowPointer++) {
////                Row row = sheet.getRow(rowPointer);
////                T t = getClassType().getConstructor().newInstance();
////                for (int i = 0; i < row.getLastCellNum(); i++) {
////                    setFieldValue(cellFieldMap.get(i), getCellValue(row, i, formulaEvaluator), t);
////                }
////                result.add(t);
////            }
////            return result;
////        }
//        return null;
//    }
//
////    private <T> void setFieldValue(Field field, Object value, T target) throws IllegalAccessException, ParseException {
////        if (Objects.nonNull(field)) {
////            field.setAccessible(true);
////            Class<?> type = field.getType();
////            if (String.class.equals(type)) {
////                field.set(target, value.toString());
////            } else if (Integer.TYPE.equals(type) || Integer.class.equals(type)) {
////                field.setInt(target, Integer.parseInt(value.toString()));
////            } else if (Long.TYPE.equals(type) || Long.class.equals(type)) {
////                field.setLong(target, Long.parseLong(value.toString()));
////            } else if (Double.TYPE.equals(type) || Double.class.equals(type)) {
////                field.setDouble(target, Double.valueOf(value.toString()));
////            } else if (Float.TYPE.equals(type) || Float.class.equals(type)) {
////                field.setFloat(target, Float.valueOf(value.toString()));
////            } else if (Boolean.TYPE.equals(type) || Boolean.class.equals(type)) {
////                field.setBoolean(target, Boolean.valueOf(value.toString()));
////            } else if (BigDecimal.class.equals(type)) {
////                field.set(target, new BigDecimal(value.toString()));
////            } else if (Date.class.equals(type)) {
////                if (value instanceof String) {
////                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                    Date parse = simpleDateFormat.parse((String) value);
////                    field.set(target, parse);
////                } else if (value instanceof Double) {
////                    field.set(target, DateUtil.getJavaDate((Double) value));
////                }
////            }
////        }
////    }
////
////    /**
////     * 构建索引，用于塞值时用到这个索引
////     *
////     * @param sheet
////     * @param titleRows
////     */
////    private void buildFieldIndex(Sheet sheet, int titleRows, FormulaEvaluator formulaEvaluator) {
////        for (; rowPointer <= titleRows; rowPointer++) {
////            Row row = sheet.getRow(rowPointer);
////            for (int i = 0; i < row.getLastCellNum(); i++) {
////                Object cellValue = getCellValue(row, i, formulaEvaluator);
////                Integer index = fieldAliasIndexMap.get(cellValue);
////                if (index != null) {
////                    // 单元字段索引
////                    cellFieldMap.put(i, this.fields[index]);
////                }
////            }
////        }
////    }
////
////    public static Object getCellValue(Row row, int index, FormulaEvaluator formulaEvaluator) {
////        Cell cell = row.getCell(index);
////        if (Objects.isNull(cell)) {
////            return null;
////        }
////        Object value = null;
////        switch (cell.getCellTypeEnum()) {
////            case STRING:
////                value = cell.getRichStringCellValue().getString();
////                ;
////                break;
////            case BOOLEAN:
////                value = cell.getBooleanCellValue();
////                break;
////            case ERROR:
////                value = cell.getErrorCellValue();
////                break;
////            case FORMULA:
////                try {
////                    CellValue evaluate = formulaEvaluator.evaluate(cell);
////                    switch (evaluate.getCellTypeEnum()) {
////                        case NUMERIC:
////                            if (DateUtil.isCellDateFormatted(cell)) {
////                                value = cell.getDateCellValue();
////                            } else {
////                                value = cell.getNumericCellValue();
////                            }
////                            break;
////                        case STRING:
////                            value = evaluate.getStringValue();
////                            break;
////                        default:
////                            value = cell.getRichStringCellValue().getString();
////                    }
////                } catch (Exception e) {
////                    value = "";
////                    LOGGER.error("公式不正确:{0}", e);
////                }
////                break;
////            case BLANK:
////                value = "";
////                break;
////            case NUMERIC:
////                CellStyle cellStyle = cell.getCellStyle();
////                if (DateUtil.isCellDateFormatted(cell)) {
////                    // 日期格式
////                    String dataFormat = "";
////                    SimpleDateFormat simpleDateFormat = null;
////                    /**
////                     * {@link BuiltinFormats}
////                     */
////                    switch (cellStyle.getDataFormat()) {
////                        case 14:
////                            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
////                            break;
////                        case 21:
////                            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
////                            break;
////                        case 164:
////                        case 22:
////                            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
////                            break;
////                        default:
////                    }
////                } else {
////                    // 数值格式
//////                    double numericCellValue = cell.getNumericCellValue();
//////                    cell.setCellType(CellType.STRING);
//////                    value = String.valueOf(cell.getRichStringCellValue().getString());
////                    value = cell.getNumericCellValue();
////                }
////                break;
////            default:
////                value = "未知类型";
////        }
////        return value;
////    }
//
//}
