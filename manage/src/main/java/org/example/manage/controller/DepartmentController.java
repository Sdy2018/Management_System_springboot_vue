package org.example.manage.controller;

import com.github.pagehelper.PageInfo;
import org.example.manage.common.Result;
import org.example.manage.entity.Department;
import org.example.manage.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class DepartmentController {


    @Autowired
    public DepartmentService departmentService;


    @GetMapping("/department")
    public Result selectAll(@RequestParam(defaultValue = "",required = false) String name) {
        return Result.success(departmentService.selectAll(name));
    }


    @PostMapping("/department")
    public Result create(@RequestBody Department Department) {
        departmentService.create(Department);
        return Result.success("增加成功");
    }

    @DeleteMapping("/department/{id}")
    public Result delete(@PathVariable int id) {
        departmentService.delete(id);
        return Result.success("删除成功");
    }

    @DeleteMapping("/department")
    public Result delete(@RequestBody List<Integer> ids) {
        departmentService.batchDelete(ids);
        return Result.success("删除成功");
    }

    @PutMapping("/department/{id}")
    public Result update(@PathVariable int id,@RequestParam(defaultValue = "0",required = false) Integer flag, @RequestBody Department Department) {
        departmentService.update(id, Department,flag);
        return Result.success("修改成功");
    }


    @GetMapping("/department/pages")
    public Result list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String name
    ) {
        PageInfo<Department> pageInfo = departmentService.getDepartment(page, size,name);
        return Result.success(pageInfo);
    }
}


