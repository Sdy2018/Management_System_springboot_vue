package org.example.manage.controller;

import com.github.pagehelper.PageInfo;
import org.example.manage.common.Result;
import org.example.manage.entity.Admin;
import org.example.manage.entity.Article;
import org.example.manage.service.AdminService;
import org.example.manage.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ArticleController {


    @Autowired
    public ArticleService articleService;


    @GetMapping("/article")
    public Result selectAll() {
        return Result.success(articleService.selectAll());
    }

    @GetMapping("/article/{id}")
    public Result selectById(@PathVariable int id) {
        return Result.success(articleService.selectById(id));
    }

    @GetMapping("/article/title")
    public Result selectByTitle(@RequestParam(required = false) String title) {
        return Result.success(articleService.selectByTitleLike(title));
    }

    @PostMapping("/article")
    public Result create(@RequestBody Article article) {
        articleService.create(article);
        return Result.success("增加成功");
    }

    @DeleteMapping("/article/{id}")
    public Result delete(@PathVariable int id) {
        articleService.delete(id);
        return Result.success("删除成功");
    }

    @DeleteMapping("/article")
    public Result delete(@RequestBody List<Integer> ids) {
//        System.out.println(ids);
//        System.out.println("---------");
        articleService.batchDelete(ids);
        return Result.success("删除成功");
    }

    @PutMapping("/article/{id}")
    public Result update(@PathVariable int id,@RequestParam(defaultValue = "0",required = false) Integer flag, @RequestBody Article article) {
        articleService.update(id, article,flag);
        return Result.success("修改成功");
    }


    @GetMapping("/article/pages")
    public Result list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageInfo<Article> pageInfo = articleService.getArticle(page, size);
        return Result.success(pageInfo);
    }
}


