package cn.com.zerobug.demo.excel.model;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/24
 */
public class ColumnStyle {

    /**
     * 列格式
     */
    private String dataFormat = "";

    /**
     * 列宽度 0为自动
     */
    private short width = 0;

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public short getWidth() {
        return width;
    }

    public void setWidth(short width) {
        this.width = width;
    }
}
