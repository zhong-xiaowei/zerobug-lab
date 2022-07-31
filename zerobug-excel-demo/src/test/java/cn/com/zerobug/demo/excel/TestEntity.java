package cn.com.zerobug.demo.excel;

import cn.com.zerobug.demo.excel.excel.ExcelCell;

import java.util.Date;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/30
 */
public class TestEntity {

    @ExcelCell(name = "用户名")
    private String userName;
    @ExcelCell(name = "年龄")
    private Integer age;
    @ExcelCell(name = "性别")
    private String gender;
    @ExcelCell(name = "生日")
    private Date birthday;
    @ExcelCell(name = "钱")
    private Double money;

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
