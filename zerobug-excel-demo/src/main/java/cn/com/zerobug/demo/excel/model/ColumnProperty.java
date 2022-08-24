package cn.com.zerobug.demo.excel.model;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/23
 */
public class ColumnProperty {

    /**
     * 列名
     */
    private String name;
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 列的样式属性
     */
    private ColumnStyle columnStyle;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnStyle getColumnStyle() {
        return columnStyle;
    }

    public void setColumnStyle(ColumnStyle columnStyle) {
        this.columnStyle = columnStyle;
    }
}
