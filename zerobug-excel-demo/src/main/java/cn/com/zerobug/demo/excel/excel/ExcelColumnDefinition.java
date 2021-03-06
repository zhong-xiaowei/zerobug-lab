package cn.com.zerobug.demo.excel.excel;

import org.apache.poi.ss.usermodel.CellStyle;

import java.lang.reflect.Field;

/**
 * @Author zhongxiaowei
 * @Date 2022/7/27 14:55
 */
public class ExcelColumnDefinition {

    private Field     field;
    private String    name;
    private String    dataType;
    private CellStyle cellStyle;

    public <T> Object getValue(T t) throws IllegalAccessException {
        return field.get(t);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
