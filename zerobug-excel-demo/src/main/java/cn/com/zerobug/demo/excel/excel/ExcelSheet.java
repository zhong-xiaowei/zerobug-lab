package cn.com.zerobug.demo.excel.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/31
 */
public class ExcelSheet extends ExcelComponent<ExcelHandler> {

    private Sheet sheet;
    private int sheetIndex;
    private String sheetName;
    private int headerRows;

    public ExcelSheet(ExcelComponent component, String sheetName, int headerRows, Sheet sheet) {
        this(component, 0, sheetName, headerRows, sheet);
    }

    public ExcelSheet(ExcelComponent component, int sheetIndex, String sheetName, int headerRows, Sheet sheet) {
        super((ExcelHandler) component);
        this.sheetName = sheetName;
        this.sheetIndex = sheetIndex;
        this.headerRows = headerRows;
        if (Objects.isNull(sheet)) {
            if (StringUtils.hasText(sheetName)) {
                this.sheet = this.previous().getWorkbook().createSheet(sheetName);
            } else {
                this.sheet = this.previous().getWorkbook().createSheet();
            }
        }
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getHeaderRows() {
        return headerRows;
    }

    public void setHeaderRows(int headerRows) {
        this.headerRows = headerRows;
    }

}
