package org.example.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.jdbc.Null;
import org.example.manage.entity.Account;
import org.example.manage.entity.Admin;
import org.example.manage.entity.Employee;
import org.example.manage.exception.BusinessException;
import org.example.manage.mapper.AdminMapper;
import org.example.manage.mapper.EmployeeMapper;
import org.example.manage.service.AdminService;
import org.example.manage.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<Admin> selectAll() {
        return adminMapper.selectAll();
    }

    @Override
    public Admin selectById(int id) {
        return adminMapper.selectById(id);
    }

    @Override
    public void create(Admin admin) {
        if(adminMapper.selectByUsername(admin.getUsername())!= null){
            throw new BusinessException(1001, "账号已存在");
        }
        if("".equals(admin.getPassword())||admin.getPassword()==null){
            admin.setPassword("123456");
        }

        if("".equals(admin.getName())&&admin.getName()==null){
            admin.setName(admin.getUsername());
        }
        admin.setRole("admin");

        adminMapper.create(admin);
    }

    @Override
    public void update(Integer id, Admin admin,Integer flag) {
        if(flag==1&&adminMapper.selectByUsername(admin.getUsername())!= null){
            throw new BusinessException(1001, "账号已存在,更新失败");
        }
        admin.setId(id);
        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            admin.setPassword("123456");
        }
        adminMapper.update(admin);
    }

    @Override
    public void delete(int id) {
        adminMapper.deleteById(id);
    }

    @Override
    public PageInfo<Admin> getAdmin(int page, int size) {
        PageHelper.startPage(page, size);

        // 执行查询（自动添加 LIMIT 子句）
        List<Admin> admin = adminMapper.selectAll();

        // 封装分页信息
        return new PageInfo<>(admin);
    }

    @Override
    public List<Admin> selectByName(String name) {
        return adminMapper.selectByName(name);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        for (Integer id : ids) {
            adminMapper.deleteById(id);
        }
    }

}
