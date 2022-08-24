package cn.com.zerobug.demo.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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
        testEntity.setIntValue(2);
        testEntity.setGender("男");
        testEntity.setBirthday(new Date());
        testEntity.setMoney(115555.22);
        testEntity.setMoney2(BigDecimal.valueOf(115555.22));

        TestEntity testEntity1 = new TestEntity();
        testEntity1.setUserName("lisi");
        testEntity1.setIntValue(1);
        testEntity1.setAge(25);
        testEntity1.setGender("男");
        testEntity1.setBirthday(new Date());
        testEntity1.setMoney(11.3);
        testEntity.setMoney2(BigDecimal.valueOf(115555.22));

        File file = new File("/Users/zhongxiaowei/Downloads/111.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        ExcelHelper.writeStream(TestEntity.class, "test1", List.of(testEntity, testEntity1), fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
