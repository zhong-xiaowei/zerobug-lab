package cn.com.zerobug.demo.excel.factory;

import cn.com.zerobug.demo.excel.annotation.ExcelCellStyle;
import cn.com.zerobug.demo.excel.annotation.ExcelColumn;
import cn.com.zerobug.demo.excel.model.ColumnProperty;
import cn.com.zerobug.demo.excel.model.ColumnStyle;
import cn.com.zerobug.demo.excel.model.ExcelDefinition;
import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/23
 */
public class ExcelDefinitionFactory {

    public static ExcelDefinition load(Class<?> clazz) {
        return loadFromAnnotation(clazz);
    }

    public static ExcelDefinition loadFromAnnotation(Class<?> clazz) {
        ExcelDefinition excelDefinition = new ExcelDefinition();
        Field[] fields = clazz.getDeclaredFields();
        List<ColumnProperty> columns = new ArrayList<>(fields.length);
        for (Field field : fields) {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            if (null != excelColumn) {
                ColumnProperty columnProperty = new ColumnProperty();
                columnProperty.setName(excelColumn.name());
                columnProperty.setFieldName(field.getName());

                ExcelCellStyle excelCellStyle = field.getAnnotation(ExcelCellStyle.class);
                ColumnStyle columnStyle = new ColumnStyle();
                if (null != excelCellStyle) {
                    columnStyle.setDataFormat(excelCellStyle.dataFormat());
                    columnStyle.setWidth(excelCellStyle.width());
                }
                columnProperty.setColumnStyle(columnStyle);

                columns.add(columnProperty);
            }
        }
        excelDefinition.setColumns(columns);
        return excelDefinition;
    }

}
