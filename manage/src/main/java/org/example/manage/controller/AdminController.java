package org.example.manage.controller;

import com.github.pagehelper.PageInfo;
import org.example.manage.common.Result;
import org.example.manage.entity.Admin;
import org.example.manage.entity.Employee;
import org.example.manage.service.AdminService;
import org.example.manage.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AdminController {


    @Autowired
    public AdminService adminService;


    @GetMapping("/admin/")
    public Result selectAll() {
        return Result.success(adminService.selectAll());
    }

    @GetMapping("/admin/{id}")
    public Result selectById(@PathVariable int id) {
        return Result.success(adminService.selectById(id));
    }

    @GetMapping("/admins")
    public Result selectByName(@RequestParam(required = false) String name) {
        return Result.success(adminService.selectByName(name));
    }

    @PostMapping("/admin/")
    public Result create(@RequestBody Admin admin) {
        adminService.create(admin);
        return Result.success("增加成功");
    }

    @DeleteMapping("/admin/{id}")
    public Result delete(@PathVariable int id) {
        adminService.delete(id);
        return Result.success("删除成功");
    }

    @DeleteMapping("/admin")
    public Result delete(@RequestBody List<Integer> ids) {
        adminService.batchDelete(ids);
        return Result.success("删除成功");
    }

    @PutMapping("/admin/{id}")
    public Result update(@PathVariable int id,@RequestParam(defaultValue = "0",required = false) Integer flag, @RequestBody Admin admin) {
        adminService.update(id, admin,flag);
        return Result.success("修改成功");
    }


    @GetMapping("/admin/pages")
    public Result list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageInfo<Admin> pageInfo = adminService.getAdmin(page, size);
        return Result.success(pageInfo);
    }
}


