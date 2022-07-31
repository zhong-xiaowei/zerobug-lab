package cn.com.zerobug.demo.excel.excel;

import java.lang.annotation.*;

/**
 * @Author zhongxiaowei
 * @Date 2022/7/27 14:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface ExcelCellStyle {

    String fontName() default "Arial";

    String fontHeightInPoints() default "10";

    String dataFormat() default "";

}
