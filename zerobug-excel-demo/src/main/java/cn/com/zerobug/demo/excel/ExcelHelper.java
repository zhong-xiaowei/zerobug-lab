package cn.com.zerobug.demo.excel;

import cn.com.zerobug.demo.excel.excel.ExcelWriter;
import cn.com.zerobug.demo.excel.factory.ExcelDefinitionFactory;
import cn.com.zerobug.demo.excel.model.ExcelDefinition;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.xml.crypto.Data;
import java.io.OutputStream;
import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/24
 */
public class ExcelHelper {

    public static void writeStream(Class<?> clazz,
                                   String sheetName,
                                   List<?> data, OutputStream outputStream) {
        ExcelDefinition excelDefinition = ExcelDefinitionFactory.load(clazz);
        ExcelWriter excelWriter = new ExcelWriter(excelDefinition, new SXSSFWorkbook());
        excelWriter.sheet(sheetName).write(data);
        excelWriter.toStream(outputStream);
    }

}
