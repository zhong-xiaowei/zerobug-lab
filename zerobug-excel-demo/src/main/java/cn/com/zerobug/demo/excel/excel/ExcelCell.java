package cn.com.zerobug.demo.excel.excel;

import org.apache.poi.ss.usermodel.CellStyle;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * @Author zhongxiaowei
 * @Date 2022/7/27 14:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface ExcelCell {

    String name();

}
