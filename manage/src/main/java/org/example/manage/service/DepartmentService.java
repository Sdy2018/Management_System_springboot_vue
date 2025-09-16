package org.example.manage.service;

import com.github.pagehelper.PageInfo;
import org.example.manage.entity.Department;

import java.util.List;


public interface DepartmentService {
    List<Department> selectAll(String name);
    Department selectById(int id);
    void create(Department department);
    void update(Integer id,Department department, Integer flag);
    void delete(int id);

    PageInfo<Department> getDepartment(int page, int size, String name);

    void batchDelete(List<Integer> ids);


}
