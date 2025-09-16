package org.example.manage.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

public class Account {
    @ExcelIgnore
    private Integer Id;

    @ExcelIgnore
    private String username;

    @ExcelIgnore
    private String password;

    @ExcelProperty("姓名") // 设置别名
    private String name;

    @ExcelProperty("角色") // 设置别名
    private String role;

    @ExcelIgnore
    private String resetPassword;

    @ExcelProperty("头像")
    private String avatar;

    public Account() {
    }

    public Account(Integer id, String username, String password, String name, String role, String resetPassword, String avatar) {
        Id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.resetPassword = resetPassword;
        this.avatar = avatar;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(String resetPassword) {
        this.resetPassword = resetPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Account{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", resetPassword='" + resetPassword + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
