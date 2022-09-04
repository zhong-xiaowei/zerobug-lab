package cn.com.zerobug.demo.excel;

import cn.com.zerobug.demo.excel.core.ExcelWriter;
import cn.com.zerobug.demo.excel.factory.ExcelDefinitionFactory;
import cn.com.zerobug.demo.excel.model.ExcelDefinition;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/24
 */
public class ExcelHelper {

    public static Workbook generateWorkbook(Class<?> clazz, String sheetName, List<?> data) {
        ExcelDefinition excelDefinition = ExcelDefinitionFactory.load(clazz);
        return new ExcelWriter(excelDefinition, new SXSSFWorkbook()).sheet(sheetName).write(data);
    }

    public static void writeStream(Workbook workbook, OutputStream outputStream) {
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
