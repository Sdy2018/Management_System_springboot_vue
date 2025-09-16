package org.example.manage.service;

import com.github.pagehelper.PageInfo;
import org.example.manage.entity.Article;

import java.util.List;

public interface ArticleService {
    List<Article> selectAll();
    Article selectById(int id);
    void create(Article article);
    void update(Integer id,Article article, Integer flag);
    void delete(int id);

    PageInfo<Article> getArticle(int page, int size);

    Article selectByTitle(String title);

    List<Article> selectByTitleLike(String title);

    void batchDelete(List<Integer> ids);
}
