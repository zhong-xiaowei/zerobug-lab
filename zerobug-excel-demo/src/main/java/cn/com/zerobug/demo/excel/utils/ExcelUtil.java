package cn.com.zerobug.demo.excel.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/24
 */
public class ExcelUtil {

    public static Workbook newSXSSFWorkbook() {
        return new SXSSFWorkbook();
    }

    public static Sheet newSheet(Workbook workbook) {
        return workbook.createSheet();
    }

    public static Sheet newSheet(Workbook workbook, String sheetName) {
        return workbook.createSheet(sheetName);
    }

    public static Row newRow(Sheet sheet, int index) {
        return sheet.createRow(index);
    }

    public static Cell newCell(Row row, int index) {
        return row.createCell(index);
    }

}
