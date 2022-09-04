package cn.com.zerobug.demo.excel.core;

import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/25
 */
public interface ReaderHandler<T> {

    void onSuccess(int sheetIndex, int rowIndex, T entity);

}
