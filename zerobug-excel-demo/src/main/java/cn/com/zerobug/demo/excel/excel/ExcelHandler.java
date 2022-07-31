package cn.com.zerobug.demo.excel.excel;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/31
 */
public abstract class ExcelHandler<T> extends ExcelComponent<Void> {

    private Workbook workbook;
    private Class<T> classType;

    public ExcelHandler(Class<T> classType) {
        super(null);
        this.classType = classType;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Class<T> getClassType() {
        return classType;
    }

    public void setClassType(Class<T> classType) {
        this.classType = classType;
    }
}
