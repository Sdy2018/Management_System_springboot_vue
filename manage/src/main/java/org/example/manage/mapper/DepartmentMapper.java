package org.example.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.manage.entity.Department;
import org.example.manage.entity.Department;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    Department selectById(@Param("id") Integer Id);
    List<Department> selectAll(String name);
    void create(Department department);
    void update(Department department);
    void deleteById(@Param("id") Integer id);
//    Employee login(@Param("username") String username);

}
