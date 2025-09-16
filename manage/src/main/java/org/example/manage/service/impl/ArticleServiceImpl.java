package org.example.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.manage.entity.Admin;
import org.example.manage.entity.Article;
import org.example.manage.exception.BusinessException;
import org.example.manage.mapper.ArticleMapper;
import org.example.manage.service.AdminService;
import org.example.manage.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<Article> selectAll() {
        return articleMapper.selectAll();
    }

    @Override
    public Article selectById(int id) {
        return articleMapper.selectById(id);
    }

    @Override
    public void create(Article article) {
        if(articleMapper.selectByTitle(article.getTitle())!= null){
            throw new BusinessException(1001, "标题已存在");
        }

        articleMapper.create(article);
    }

    @Override
    public void update(Integer id, Article article,Integer flag) {
        if(flag==1&&articleMapper.selectByTitle(article.getTitle())!= null){
            throw new BusinessException(1001, "账号已存在,更新失败");
        }
        article.setId(id);

        articleMapper.update(article);
    }

    @Override
    public void delete(int id) {
        articleMapper.deleteById(id);
    }

    @Override
    public PageInfo<Article> getArticle(int page, int size) {
        PageHelper.startPage(page, size);

        // 执行查询（自动添加 LIMIT 子句）
        List<Article> articles = articleMapper.selectAll();

        // 封装分页信息
        return new PageInfo<>(articles);
    }

    @Override
    public Article selectByTitle(String title) {
        return articleMapper.selectByTitle(title);
    }

    @Override
    public List<Article> selectByTitleLike(String title) {
        return articleMapper.selectByTitleLike(title);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        for (Integer id : ids) {
            articleMapper.deleteById(id);
        }
    }

}
