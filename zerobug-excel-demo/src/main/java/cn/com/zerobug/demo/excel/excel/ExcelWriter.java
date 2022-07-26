package cn.com.zerobug.demo.excel.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/26
 */
public class ExcelWriter<T> {

    private Workbook      workbook;
    private Sheet         sheet;
    private Collection<T> data;
    private Class<T>      classType;
    private Field[]       fields;

    public static <T> ExcelWriter create(Class<T> classType) {
        ExcelWriter<T> excelBuilder = new ExcelWriter<>();
        excelBuilder.workbook = new SXSSFWorkbook();
        return excelBuilder;
    }

    public ExcelWriter sheet() {
        if (sheet == null) {
            sheet = this.workbook.createSheet();
        }
        return this;
    }

    public ExcelWriter into(Collection<T> data) {
        if (data == null) {
            this.data = Collections.emptyList();
            return this;
        }
        this.data = data;
        return this;
    }

}
