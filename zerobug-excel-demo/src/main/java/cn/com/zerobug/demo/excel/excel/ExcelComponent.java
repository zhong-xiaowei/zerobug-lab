package cn.com.zerobug.demo.excel.excel;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/31
 */
public abstract class ExcelComponent<P> {

    private P node;

    public ExcelComponent(P node) {
        this.node = node;
    }

    protected P previous() {
        return node;
    }

}
