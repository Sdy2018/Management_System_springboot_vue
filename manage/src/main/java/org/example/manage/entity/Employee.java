package org.example.manage.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.manage.common.CharacterConverter;

import java.time.LocalDateTime;
import java.util.Date;


public class Employee  extends Account{


    //    name, gender, age, hire_date, position, department, salary, id_card, email
//    private Integer employeeId;


    @ExcelProperty(value = "性别",converter = CharacterConverter.class)
    private Character gender;

    @ExcelProperty(value = "年龄")
    private Integer age;

    @ExcelProperty(value = "入职日期")
    private Date hireDate;

    @ExcelProperty(value = "职位")
    private String position;

    @ExcelProperty(value = "部门编号")
    private Integer departmentId;

    @ExcelProperty(value = "部门")
    private String departmentName;

    @ExcelProperty(value = "薪水")
    private Double salary;

    @ExcelProperty(value = "身份证")
    private String idCard;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty(value = "简介")
    private  String introduction;




    public Employee() {
        super();
    }



    public Employee(Account account) {
      super(account.getId(), account.getUsername(), account.getPassword(),   account.getName(), account.getRole(), account.getResetPassword(), account.getAvatar());
    }


    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
