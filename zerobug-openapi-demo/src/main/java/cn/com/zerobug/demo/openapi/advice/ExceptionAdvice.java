package cn.com.zerobug.demo.openapi.advice;

import cn.hutool.poi.excel.ExcelReader;
import com.dd.component.excel.exception.ExcelReadException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/30
 */
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ExcelReadException.class)
    public String handleExcelReadException(ExcelReadException ex) {
        return ex.getMessage();
    }

}
