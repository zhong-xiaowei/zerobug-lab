package cn.com.zerobug.demo.excel.annotation;

import java.lang.annotation.*;

/**
 * @Author zhongxiaowei
 * @Date 2022/7/27 14:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface ExcelColumn {

    /**
     * @return 单元格名称
     */
    String name();

    /**
     * @return 对应字段名
     */
    String fieldName() default "";


}
