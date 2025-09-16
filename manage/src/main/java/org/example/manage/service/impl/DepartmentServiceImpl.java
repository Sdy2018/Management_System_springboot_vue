package org.example.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.manage.entity.Department;
import org.example.manage.exception.BusinessException;
import org.example.manage.mapper.DepartmentMapper;
import org.example.manage.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;


    @Override
    public List<Department> selectAll(String name) {
        return departmentMapper.selectAll(name);
    }

    @Override
    public Department selectById(int id) {
        return departmentMapper.selectById(id);
    }

    @Override
    public void create(Department department) {
        departmentMapper.create(department);
    }

    @Override
    public void update(Integer id, Department department,Integer flag) {

        departmentMapper.update(department);
    }

    @Override
    public void delete(int id) {
        departmentMapper.deleteById(id);
    }

    @Override
    public PageInfo<Department> getDepartment(int page, int size,String name) {
        PageHelper.startPage(page, size);

        // 执行查询（自动添加 LIMIT 子句）
        List<Department> department = departmentMapper.selectAll(name);

        // 封装分页信息
        return new PageInfo<>(department);
    }


    @Override
    public void batchDelete(List<Integer> ids) {
        for (Integer id : ids) {
            departmentMapper.deleteById(id);
        }
    }



}
