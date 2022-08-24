package cn.com.zerobug.demo.excel.model;

import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/23
 */
public class ExcelDefinition {

    List<ColumnProperty> columns;

    public List<ColumnProperty> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnProperty> columns) {
        this.columns = columns;
    }
}
