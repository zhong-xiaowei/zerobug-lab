package cn.com.zerobug.demo.excel.excel;

import cn.com.zerobug.demo.excel.model.ColumnProperty;
import cn.com.zerobug.demo.excel.model.ColumnStyle;
import cn.com.zerobug.demo.excel.model.ExcelDefinition;
import cn.com.zerobug.demo.excel.utils.ExcelUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/26
 */
public class ExcelWriter {

    private ExcelDefinition excelDefinition;
    private Workbook workbook;
    private String sheetName;
    private boolean init = false;

    public ExcelWriter(ExcelDefinition excelDefinition, Workbook workbook) {
        this.excelDefinition = excelDefinition;
        this.workbook = workbook;
    }

    public ExcelWriter sheet(String sheetName) {
        this.initSheetHeader(sheetName);
        this.sheetName = sheetName;
        this.init = true;
        return this;
    }

    public Workbook write(List<?> data) {
        if (init) {
            if (!CollectionUtils.isEmpty(data)) {
                Sheet sheet = workbook.getSheet(sheetName);
                List<ColumnProperty> columns = this.excelDefinition.getColumns();
                for (int i = 0; i < data.size(); i++) {
                    Row row = ExcelUtil.newRow(sheet, i + 1);
                    for (int j = 0; j < columns.size(); j++) {
                        this.setCellValue(ExcelUtil.newCell(row, j), data.get(i), columns.get(j));
                    }
                }
            }
        }
        return workbook;
    }

    public void toStream(OutputStream outputStream) {
        try {
            this.workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                this.workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initSheetHeader(String sheetName) {
        Sheet sheet = ExcelUtil.newSheet(workbook, sheetName);
        Row headerRow = ExcelUtil.newRow(sheet, 0);
        List<ColumnProperty> columns = excelDefinition.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            ColumnProperty columnProperty = columns.get(i);
            ColumnStyle columnStyle = columnProperty.getColumnStyle();
            this.setCellWidth(sheet, i, columnStyle.getWidth(), columnProperty.getName());
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(getHeaderCellStyle());
            cell.setCellValue(columnProperty.getName());
        }
    }

    private CellStyle getHeaderCellStyle() {
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
        return headerCellStyle;
    }

    private void setCellWidth(Sheet sheet, int index, Short width, String name) {
        if (width == 0 && StringUtils.hasLength(name)) {
            sheet.setColumnWidth(index, (short) (name.length() * 2000));
        } else {
            width = width == 0 ? 150 : width;
            sheet.setColumnWidth(index, (short) (width * 35));
        }
    }

    private void setCellValue(Cell cell, Object entity, ColumnProperty columnProperty) {
        Object value = BeanUtil.getFieldValue(entity, columnProperty.getFieldName());
        if (null != value) {
            ColumnStyle columnStyle = columnProperty.getColumnStyle();
            String dataFormat = columnStyle.getDataFormat();
            if (StringUtils.hasLength(dataFormat)) {
                if (value instanceof Date) {
                    cell.setCellValue(DateUtil.format((Date) value, dataFormat));
                    return;
                }
                // TODO ...更多格式处理
            }
            cell.setCellValue(Convert.toStr(value));
        }
    }

}
