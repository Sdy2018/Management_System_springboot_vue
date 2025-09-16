package org.example.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.manage.entity.Account;
import org.example.manage.entity.Employee;
import org.example.manage.exception.BusinessException;
import org.example.manage.exception.SystemException;
import org.example.manage.mapper.EmployeeMapper;
import org.example.manage.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int USERNAME_LENGTH = 8; // 自定义长度

    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public List<Employee> selectAll() {
        return employeeMapper.selectAll();
    }

    @Override
    public Employee selectById(int id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public void create(Employee employee) {
        Employee employee1  =employeeMapper.selectByUsername(employee.getUsername());
        if(employee1!=null){
            throw new BusinessException(1001, "账号已存在");
        }
        if(employee.getUsername()==null){
            employee.setUsername(generateUniqueUsername());
        }

        if("".equals(employee.getPassword())||employee.getPassword()==null){
            employee.setPassword("user123456");
        }
        employee.setRole("user");
        employeeMapper.create(employee);
    }



    public String generateUniqueUsername() {
        for (;;) {
            String username = generateRandomUsername();
            if (employeeMapper.selectByUsername(username) == null) {
                return username;
            }
        }
    }

    private String generateRandomUsername() {
        StringBuilder sb = new StringBuilder(USERNAME_LENGTH);
        Random random = new Random();
        for (int i = 0; i < USERNAME_LENGTH; i++) {
            sb.append(CHAR_SET.charAt(random.nextInt(CHAR_SET.length())));
        }
        return sb.toString();
    }
    @Transactional
    @Override
    public void update(Integer id, Employee employee, Integer flag) {
        if(flag==1&&employeeMapper.selectByUsername(employee.getUsername())!= null ){
            throw new BusinessException(1001, "账号已存在,更新失败");
        }
        employee.getEmail();
        List<Map<String,Object>> data= employeeMapper.checkEmail( employee.getId(),employee.getEmail());
        if (!data.isEmpty()){
            throw new BusinessException(1001, "邮箱已存在");
        }

        List<Map<String,Object>> idCardData= employeeMapper.checkIdCard( employee.getId(),employee.getIdCard());
        if (!idCardData.isEmpty()){
            throw new BusinessException(1001, "身份证已存在");
        }



        employee.setId(id);
        employeeMapper.update(employee);
    }

    @Override
    public void delete(int id) {
        employeeMapper.deleteById(id);
    }

    @Transactional
    @Override
    public PageInfo<Employee> getEmployees(int page, int size) {
        PageHelper.startPage(page, size);

        // 执行查询（自动添加 LIMIT 子句）
        List<Employee> employees = employeeMapper.selectAll();

        // 封装分页信息
        return new PageInfo<>(employees);
    }

    @Override
    public List<Employee> selectByName(String name) {
        return employeeMapper.selectByName(name);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        for (Integer id : ids) {
            employeeMapper.deleteById(id);
        }
    }

    @Override
    public List<Map<String, Object>> countEmployeesByDept() {
        return employeeMapper.countEmployeesByDept();
    }

    @Override
    public Map<String, Object> salaryDistribution() {
        return employeeMapper.salaryDistribution();
    }

    @Override
    public List<Map<String, Object>> getHireStatsByYear() {
        return employeeMapper.getHireStatsByYear();
    }

    @Override
    public List<Map<String, Object>> ageStatistic() {
        return employeeMapper.ageStatistic();
    }



}
