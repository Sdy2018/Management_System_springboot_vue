package org.example.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.manage.entity.Account;
import org.example.manage.entity.Admin;
import org.example.manage.entity.Employee;

import java.util.List;

@Mapper
public interface AdminMapper {
    Admin selectById(@Param("id") Integer Id);
    List<Admin> selectAll();
    void create(Account account);
    void update(Admin user);
    void deleteById(@Param("id") Integer id);
    List<Admin> selectByName(String name);
//    Employee login(@Param("username") String username);
    Account selectByUsername(String username);

}
