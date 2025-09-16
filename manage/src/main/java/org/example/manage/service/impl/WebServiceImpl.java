package org.example.manage.service.impl;

import org.example.manage.entity.Account;
import org.example.manage.entity.Admin;
import org.example.manage.entity.Employee;
import org.example.manage.exception.BusinessException;
import org.example.manage.mapper.AdminMapper;
import org.example.manage.mapper.EmployeeMapper;
import org.example.manage.service.AdminService;
import org.example.manage.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WebServiceImpl implements WebService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    AdminService adminService;


    @Override
    public Account login(Account account) {

        if ("admin".equals(account.getRole())) {
            return getAccount(account, adminMapper.selectByUsername(account.getUsername()));
        } else if ("user".equals(account.getRole())) {
            return getAccount(account, employeeMapper.selectByUsername(account.getUsername()));
        }
        throw new BusinessException(500, "角色类型错误");
    }

    @Override
    public void reset(Account account) {
        if(account.getRole().equals("user")){
            System.out.println(account);
            System.out.println("user");
            Employee employee=employeeMapper.selectById(account.getId());
            if(!employee.getPassword().equals(account.getPassword())){
                throw new BusinessException(1002, "原密码错误");
            }
            account.setPassword(account.getResetPassword());
            employeeMapper.update(new Employee(account));
        } else if (account.getRole().equals("admin")) {
            Admin admin=adminMapper.selectById(account.getId());
            if(!admin.getPassword().equals(account.getPassword())){
                throw new BusinessException(1002, "原密码错误");
            }
            account.setPassword(account.getResetPassword());
            adminMapper.update(new Admin(account));
        }
    }

    private Account getAccount(Account account, Account queryAcc) {
        if (queryAcc==null) {
            throw new BusinessException(1001, "账户不存在");
        }
        if (!Objects.equals(queryAcc.getPassword(), account.getPassword())) {
            throw new BusinessException(1002, "密码错误");
        }
        return queryAcc;
    }



    @Override
    public void register(Account account) {
        Employee logEmployees = employeeMapper.selectByUsername(account.getUsername());
        if (logEmployees != null) {
            throw new BusinessException(1001, "账户已存在");
        }else{
            account.setRole("user");
            employeeMapper.create(new Employee(account));
        }

    }
}
