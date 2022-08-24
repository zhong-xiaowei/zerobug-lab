package cn.com.zerobug.demo.excel;

import cn.com.zerobug.demo.excel.annotation.ExcelCellStyle;
import cn.com.zerobug.demo.excel.annotation.ExcelColumn;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/30
 */
public class TestEntity {

    @ExcelColumn(name = "用户名")
    private String userName;
    @ExcelColumn(name = "年龄")
    private Integer age;
    @ExcelColumn(name = "int")
    private int intValue;
    @ExcelColumn(name = "性别")
    private String gender;
    @ExcelColumn(name = "生日")
    @ExcelCellStyle(dataFormat = "yyyy-MM-dd")
    private Date birthday;
    @ExcelColumn(name = "钱")
    private Double money;
    @ExcelColumn(name = "钱2")
    private BigDecimal money2;

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public BigDecimal getMoney2() {
        return money2;
    }

    public void setMoney2(BigDecimal money2) {
        this.money2 = money2;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", money=" + money +
                '}';
    }
}
