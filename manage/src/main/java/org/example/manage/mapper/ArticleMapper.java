package org.example.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.manage.entity.Account;
import org.example.manage.entity.Admin;
import org.example.manage.entity.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    Article selectById(@Param("id") Integer Id);
    List<Article> selectAll();
    void create(Article article);
    void update(Article article);
    void deleteById(@Param("id") Integer id);
    List<Article> selectByTitleLike(String title);
    Article selectByTitle(String title);
//    Employee login(@Param("username") String username);
//    Account selectByUsername(String username);

}
