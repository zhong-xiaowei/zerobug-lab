package cn.com.zerobug.demo.excel.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/26
 */
public class ExcelWriter<T> extends ExcelHandler<T> {

    public static final Integer DEFAULT_WORKBOOK = 1;

    private ExcelSheet excelSheet;
    private int sheetCursor;
    private List<ExcelColumnDefinition> columnDefinitions;

    public ExcelWriter(Class<T> classType) {
        this(classType, DEFAULT_WORKBOOK);
    }

    public ExcelWriter(Class<T> classType, Integer type) {
        super(classType);
        if (DEFAULT_WORKBOOK.equals(type)) {
            setWorkbook(new XSSFWorkbook());
        }
        registerExcelColumnDefinition();

    }

    /**
     * 创建或获取sheet
     * 默认为上一次操作的sheet
     * 如果第一次则为0
     *
     * @return ExcelWriter
     */
    public ExcelWriter<T> sheet() {
        return sheet(null);
    }

    /**
     * 创建或获取sheet 根据游标选择
     *
     * @param sheetName
     * @return
     */
    public ExcelWriter<T> sheet(String sheetName) {
        this.excelSheet = new ExcelSheet(this, 0, null, 1, null);
        return this;
    }


    /**
     * 数据来源
     *
     * @param datas 数据集合
     * @return ExcelWriter
     */
    public ExcelWriter<T> from(List<T> datas) {
        for (int i = 0; i < datas.size(); i++) {
            T t = datas.get(i);
            Row row = excelSheet.getSheet().createRow(i + 1);
            for (int j = 0; j < columnDefinitions.size(); j++) {
                ExcelColumnDefinition definition = columnDefinitions.get(j);
                setCellValue(row.createCell(j), t, definition);
            }
        }
        return this;
    }

    /**
     * 数据写入
     *
     * @param outputStream
     */
    public void doWrite(OutputStream outputStream) throws IOException {
        try {
            getWorkbook().write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    /**
     * 关闭工作簿
     *
     * @throws IOException
     */
    public void close() throws IOException {
        getWorkbook().close();
    }

    /**
     * 构建表头
     */
    public ExcelWriter<T> buildHeader() {
        Sheet sheet = excelSheet.getSheet();
        Row row = sheet.createRow(0);
        CellStyle headerCellStyle = getWorkbook().createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        Font titleFont = getWorkbook().createFont();
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
        return this;
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
            DataFormat dataFormat = getWorkbook().createDataFormat();
            CellStyle cellStyle = definition.getCellStyle();
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
                String builtinFormat = BuiltinFormats.getBuiltinFormat(14);
                short format = dataFormat.getFormat(builtinFormat);
                cellStyle.setDataFormat(format);
                cell.setCellValue((Date) value);
            }
            cell.setCellStyle(definition.getCellStyle());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册Excel 的字段定义，用于后期的写入
     */
    private void registerExcelColumnDefinition() {
        Field[] fields = getClassType().getDeclaredFields();
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
            CellStyle cellStyle = getWorkbook().createCellStyle();
            Font font = getWorkbook().createFont();
            if (excelCellStyle != null) {
                setCustomStyle(excelCellStyle, cellStyle, font);
            } else {
                // 默认样式
                setDefualtStyle(cellStyle, font);
            }
            excelColumnDefinition.setCellStyle(cellStyle);
            columnDefinitions.add(excelColumnDefinition);
        }
    }

    private void setCustomStyle(ExcelCellStyle excelCellStyle, CellStyle cellStyle, Font font) {
        // 自定义样式，等待完善
        font.setFontName(excelCellStyle.fontName());
        font.setFontHeightInPoints(Short.valueOf(excelCellStyle.fontHeightInPoints()));
        cellStyle.setFont(font);
        if (!excelCellStyle.dataFormat().isEmpty()) {
            short format = getWorkbook().createDataFormat().getFormat(excelCellStyle.dataFormat());
            cellStyle.setDataFormat(format);
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
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
    }

}
