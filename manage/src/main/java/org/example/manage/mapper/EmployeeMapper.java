package org.example.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.manage.entity.Employee;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper {
    Employee selectById(@Param("id") Integer Id);
    List<Employee> selectAll();
    void create(Employee user);
    void update(Employee user);
    void deleteById(@Param("id") Integer id);
    List<Employee> selectByName(String name);
//    Employee login(@Param("username") String username);
    Employee selectByUsername( String username);

    List<Map<String, Object>> countEmployeesByDept();

    Map<String,Object>  salaryDistribution();

    List<Map<String,Object>>  getHireStatsByYear();

    List<Map<String,Object>>  ageStatistic();

    List<Map<String, Object>>  checkEmail(Integer id,String email);

    List<Map<String, Object>>  checkIdCard(Integer id,String idCard);
}
