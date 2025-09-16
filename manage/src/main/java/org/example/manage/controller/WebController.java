package org.example.manage.controller;


import org.example.manage.common.Result;
import org.example.manage.entity.Account;
import org.example.manage.entity.Admin;
import org.example.manage.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WebController {

    @Autowired
    WebService webService;

    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account account1=webService.login(account);
        return new  Result(200,"登录成功",account1);
    }



    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        webService.register(account);
        return Result.success("注册成功");
    }

    @PutMapping("/reset")
    public Result resetPassword(@RequestBody Account account) {
        webService.reset(account);
        return Result.success("修改成功");
    }
}
