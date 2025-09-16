package org.example.manage.service;

import com.github.pagehelper.PageInfo;
import org.example.manage.entity.Admin;


import java.util.List;


public interface AdminService {
    List<Admin> selectAll();
    Admin selectById(int id);
    void create(Admin admin);
    void update(Integer id,Admin admin, Integer flag);
    void delete(int id);

    PageInfo<Admin> getAdmin(int page, int size);

    List<Admin> selectByName(String name);

    void batchDelete(List<Integer> ids);
}
