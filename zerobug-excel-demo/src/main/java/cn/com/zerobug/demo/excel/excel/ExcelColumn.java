package cn.com.zerobug.demo.excel.excel;

import java.lang.annotation.*;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface ExcelColumn {

    String name() default "";

}
