package cn.com.zerobug.demo.openapi.controller;


import com.dd.component.excel.annotation.ExcelCellStyle;
import com.dd.component.excel.annotation.ExcelColumn;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/30
 */
public class TestEntity {

    @ExcelColumn(name = "用户名")
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    @ExcelColumn(name = "年龄")
    @NotNull(message = "年龄不能为空")
    private Integer age;
    @ExcelColumn(name = "int")
    @NotNull(message = "int不能为空")
    private int intValue;
    @ExcelColumn(name = "性别")
    @NotEmpty(message = "性别不能为空")
    private String gender;
    @ExcelColumn(name = "生日")
    @ExcelCellStyle(dataFormat = "yyyy-MM-dd")
    @NotNull(message = "生日不能为空")
    private Date birthday;
    @ExcelColumn(name = "钱")
    @NotNull(message = "钱不能为空")
    private Double money;
    @ExcelColumn(name = "钱2")
    @ExcelCellStyle(dataFormat = "2")
    @NotNull(message = "钱2不能为空")
    private BigDecimal money2;

    public int getIntValue() {
        return this.intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public BigDecimal getMoney2() {
        return this.money2;
    }

    public void setMoney2(BigDecimal money2) {
        this.money2 = money2;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getMoney() {
        return this.money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "TestEntity{" + "userName='" + this.userName + '\'' + ", age=" + this.age + ", intValue=" + this.intValue + ", gender='" + this.gender + '\'' + ", birthday=" + this.birthday + ", money=" + this.money + ", money2=" + this.money2 + '}';
    }
}
