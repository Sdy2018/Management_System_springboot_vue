package org.example.manage.service;

import com.github.pagehelper.PageInfo;
import org.example.manage.common.ApiResponse;
import org.example.manage.entity.Employee;

import java.util.List;
import java.util.Map;


public interface EmployeeService {
    List<Employee> selectAll();
    Employee selectById(int id);
    void create(Employee employee);
    void update(Integer id,Employee employee,Integer flag);
    void delete(int id);

    PageInfo<Employee> getEmployees(int page, int size);

    List<Employee> selectByName(String name);

    void batchDelete(List<Integer> ids);

    List<Map<String, Object>> countEmployeesByDept();

    Map<String,Object>  salaryDistribution();

    List<Map<String, Object>>  getHireStatsByYear();

    List<Map<String, Object>>  ageStatistic();

}
