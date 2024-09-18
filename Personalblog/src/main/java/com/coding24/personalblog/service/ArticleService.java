package com.coding24.personalblog.service;

import com.coding24.personalblog.entity.Article;

import java.util.List;

public interface ArticleService {
    //通过文章ID查询文章详情
    Article queryById(Integer id);
    //获取所有文章列表
    List<Article> getAll();
    //添加新文章
    int add(Article article);
    //更新现有文章
    int update(Article article);
    //通过文章ID删除文章
    int delete(Integer id);
    //通过用户ID删除文章
    int deletebyuserid(Integer id);
}
