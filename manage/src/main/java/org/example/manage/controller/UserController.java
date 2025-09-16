package org.example.manage.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.jdbc.Null;
import org.example.manage.common.CharacterConverter;
import org.example.manage.common.Result;
import org.example.manage.entity.Employee;
import org.example.manage.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    public EmployeeService employeeService;


    @GetMapping("/employee")
    public  Result selectAll() {
        return Result.success(employeeService.selectAll());
    }

    @GetMapping("/employee/{id}")
    public  Result selectById(@PathVariable int id) {
        return Result.success(employeeService.selectById(id));
    }

    @GetMapping("/employees")
    public  Result selectByName(@RequestParam(required = false) String name) {
        return Result.success(employeeService.selectByName(name));
    }

    @PostMapping("/employee")
    public Result create(@RequestBody Employee employee){
        System.out.println(employee);
        employeeService.create(employee);
        return Result.success("增加成功");
    }
    @DeleteMapping("/employee/{id}")
    public Result delete(@PathVariable int id) {
        employeeService.delete(id);
        return Result.success("删除成功");
    }

    @DeleteMapping("/employee")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
//        System.out.println(ids);
//        System.out.println("---------");
        employeeService.batchDelete(ids);
        return Result.success("删除成功");
    }

    @PutMapping("/employee/{id}")
    public Result update(@PathVariable int id, @RequestParam(defaultValue = "0",required = false) Integer flag, @RequestBody Employee employee){
        System.out.println("ssss");
        employeeService.update(id,employee,flag);
        return Result.success("修改成功");
    }



    @GetMapping("/employee/csv")
    public void exportUsers(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("员工列表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 查询数据
        List<Employee> dataList = employeeService.selectAll();

        System.out.println(dataList);

        // 写入 Excel 并输出到响应流
        EasyExcel.write(response.getOutputStream(), Employee.class)
                .registerConverter(new CharacterConverter())
                .sheet("员工列表")
                .doWrite(dataList);
    }



    @PostMapping("/employee/csv")
    public Result importExcel(@RequestParam("file") MultipartFile file) throws IOException {
            List<Employee> employeeList = EasyExcel.read(file.getInputStream())
                    .head(Employee.class)
                    .excelType( ExcelTypeEnum.XLSX)
                    .registerConverter(new CharacterConverter())
                    .sheet()
                    .doReadSync();
            for (Employee employee : employeeList) {
                employeeService.create(employee);
            }
            return Result.success();
    }



    @GetMapping("/employee/pages")
    public Result list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageInfo<Employee> pageInfo = employeeService.getEmployees(page, size);
        return Result.success(pageInfo);
    }


    @GetMapping("/dpt-count")
    public Result emDepartCount() {
        List<Map<String, Object>> deptCount = employeeService.countEmployeesByDept();
        return Result.success(deptCount);
    }

    @GetMapping("/salary-distribution")
    public Result salaryDistribution(){
        return Result.success(employeeService.salaryDistribution());
    }


    @GetMapping("/hire-stats-by-year")
    public Result getHireStatsByYear(){
        return Result.success(employeeService.getHireStatsByYear());
    }


    @GetMapping("/age-statistic")
    public Result ageStatistic(){
        return Result.success(employeeService.ageStatistic());
    }

//    @PostMapping("/login")
//    public Result login(@RequestBody Employee employee){
//
////        try {
//            Employee employee1 = employeeService.login(employee);
//            // 登录成功后的处理逻辑
//            return Result.success(employee1);
////        } catch (BusinessException e) {
////            // 异常处理逻辑
////            System.out.println("登录失败: " + e.getMessage());
////            // 可以根据不同类型的异常进行不同处理
////            return Result.error(e.getCode(),e.getMessage());
////        }
//    }
//
//
//    @PostMapping("/register")
//    public Result register(@RequestBody Employee employee){
//
//
//        employeeService.register(employee);
//        // 登录成功后的处理逻辑
//        return  Result.success("注册成功");
//    }

}
