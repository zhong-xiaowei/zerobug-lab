package cn.com.zerobug.demo.excel.excel;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/31
 */
public class ExcelFactory {

    public static <T> ExcelWriter<T> writer(Class<T> clazz) {
        return new ExcelWriter<>(clazz);
    }

    public static <T> ExcelReader<T> reader(Class<T> clazz) {
        return new ExcelReader<>(clazz);
    }

}
