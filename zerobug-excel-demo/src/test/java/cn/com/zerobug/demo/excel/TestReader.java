package cn.com.zerobug.demo.excel;

import cn.com.zerobug.demo.excel.excel.ExcelFactory;
import cn.com.zerobug.demo.excel.excel.ExcelReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/30
 */
public class TestReader {

    public static void main(String[] args) throws Exception {

        File file = new File("/Users/zhongxiaowei/Downloads/111.xlsx");
        List<TestEntity> testEntities = ExcelFactory.reader(TestEntity.class)
                .from(new FileInputStream(file))
                .sheet(1)
                .doRead();
        System.out.println(testEntities);
    }


}
