package cn.com.zerobug.demo.excel.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/26
 */
public class ExcelWriter<T> {

    private Workbook workbook;
    private Sheet[] sheets;
    private int sheetCursor;
    private Class<T>                    classType;
    private List<ExcelColumnDefinition> columnDefinitions;

    /**
     * 创建ExcelWriter
     *
     * @param classType java类
     * @param <T>
     * @return ExcelWriter
     */
    public static <T> ExcelWriter<T> create(Class<T> classType) {
        return new ExcelWriter<T>().init(classType);
    }

    private ExcelWriter<T> init(Class<T> classType) {
        this.workbook = new SXSSFWorkbook();
        this.classType = classType;
        this.sheets = new Sheet[4];
        registerExcelColumnDefinition();
        return this;
    }

    /**
     * 创建或获取sheet
     * 默认为上一次操作的sheet
     * 如果第一次则为0
     *
     * @return ExcelWriter
     */
    public ExcelWriter<T> sheet() {
        sheet(sheetCursor);
        return this;
    }

    /**
     * 创建或获取sheet 根据游标选择
     *
     * @param sheetCursor
     * @return
     */
    public ExcelWriter<T> sheet(int sheetCursor) {
        this.sheetCursor = sheetCursor;
        if (sheetCursor > sheets.length - 1) {
            this.sheets = Arrays.copyOf(this.sheets, this.sheets.length * 2);
        }
        if (sheets[sheetCursor] == null) {
            Sheet sheet = this.workbook.createSheet();
            sheets[sheetCursor] = sheet;
            buildHeader(sheet);
        }
        return this;
    }

    /**
     * 写入数据
     *
     * @param datas 数据集合
     * @return ExcelWriter
     */
    public ExcelWriter<T> write(List<T> datas) {
        for (int i = 0; i < datas.size(); i++) {
            T   t   = datas.get(i);
            Row row = this.sheets[sheetCursor].createRow(i + 1);
            for (int j = 0; j < columnDefinitions.size(); j++) {
                ExcelColumnDefinition definition = columnDefinitions.get(j);
                setCellValue(row.createCell(j), t, definition);
            }
        }
        return this;
    }

    /**
     * 转换成 流
     *
     * @param outputStream
     */
    public void toStream(OutputStream outputStream) {
        try {
            this.workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建表头
     *
     * @param sheet sheet
     */
    private void buildHeader(Sheet sheet) {
        Row       row             = sheet.createRow(0);
        CellStyle headerCellStyle = this.workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        Font titleFont = this.workbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 14);
        titleFont.setBold(true);
        headerCellStyle.setFont(titleFont);

        for (int i = 0; i < columnDefinitions.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnDefinitions.get(i).getName());
            cell.setCellStyle(headerCellStyle);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 18 / 9);
        }
    }

    /**
     * 设置单元 值
     *
     * @param cell       单元
     * @param t          数据类型
     * @param definition 字段定义
     */
    private void setCellValue(Cell cell, T t, ExcelColumnDefinition definition) {
        try {
            cell.setCellStyle(definition.getCellStyle());
            Object value = definition.getValue(t);
            if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Double) {
                cell.setCellValue((Double) value);
            } else if (value instanceof Date) {
                cell.setCellValue((Date) value);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册Excel 的字段定义，用于后期的写入
     */
    private void registerExcelColumnDefinition() {
        Field[] fields = this.classType.getDeclaredFields();
        this.columnDefinitions = new ArrayList<>(fields.length);
        for (Field field : fields) {
            field.setAccessible(true);
            ExcelColumnDefinition excelColumnDefinition = new ExcelColumnDefinition();
            excelColumnDefinition.setField(field);
            ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
            if (excelCell != null) {
                excelColumnDefinition.setName(excelCell.name());
            }
            ExcelCellStyle excelCellStyle = field.getAnnotation(ExcelCellStyle.class);
            CellStyle cellStyle = this.workbook.createCellStyle();
            Font font = this.workbook.createFont();
            if (excelCellStyle != null) {
                // 自定义样式，等待完善
                font.setFontName(excelCellStyle.fontName());
                font.setFontHeightInPoints(Short.valueOf(excelCellStyle.fontHeightInPoints()));
                cellStyle.setFont(font);
                cellStyle.setDataFormat(this.workbook.createDataFormat().getFormat(excelCellStyle.dataFormat()));
            } else {
                // 默认样式
                setDefualtStyle(cellStyle, font);
            }
            excelColumnDefinition.setCellStyle(cellStyle);
            columnDefinitions.add(excelColumnDefinition);
        }
    }

    /**
     * 设置默认的 数据单元样式
     *
     * @param cellStyle
     * @param font
     */
    private void setDefualtStyle(CellStyle cellStyle, Font font) {
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setDataFormat(this.workbook.createDataFormat().getFormat("yyyy/MM/dd"));
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
    }

}
