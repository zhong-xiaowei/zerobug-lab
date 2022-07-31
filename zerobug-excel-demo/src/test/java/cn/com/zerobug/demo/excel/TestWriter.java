package cn.com.zerobug.demo.excel;

import cn.com.zerobug.demo.excel.excel.ExcelFactory;
import cn.com.zerobug.demo.excel.excel.ExcelWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/30
 */
public class TestWriter {

    public static void main(String[] args) throws IOException {
        TestEntity testEntity = new TestEntity();
        testEntity.setUserName("zhangsan");
        testEntity.setAge(23);
        testEntity.setGender("男");
        testEntity.setBirthday(new Date());
        testEntity.setMoney(115555.22);

        TestEntity testEntity1 = new TestEntity();
        testEntity1.setUserName("lisi");
        testEntity1.setAge(25);
        testEntity1.setGender("男");
        testEntity1.setBirthday(new Date());
        testEntity1.setMoney(11.3);

        File file = new File("/Users/zhongxiaowei/Downloads/111.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);


        ExcelFactory.writer(TestEntity.class)
                .sheet()
                .from(List.of(testEntity1, testEntity))
                .doWrite(fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
