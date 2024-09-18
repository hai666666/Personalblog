package com.coding24.personalblog.service.impl;

import com.coding24.personalblog.entity.Article;
import com.coding24.personalblog.mapper.ArticleMapper;
import com.coding24.personalblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    // 根据文章ID查询文章信息
    @Override
    public Article queryById(Integer id){
        return articleMapper.queryById(id);
    }

    @Override
    public List<Article> getAll() {
        return articleMapper.getAll();
    }

    // 添加文章信息
    @Override
    public int add(Article article) {
        return articleMapper.add(article);
    }

    // 更新文章信息
    @Override
    public int update(Article article) {
        return articleMapper.update(article);
    }

    // 根据文章ID删除文章信息
    @Override
    public int delete(Integer id) {
        return articleMapper.delete(id);
    }

    // 根据用户ID删除文章信息
    @Override
    public int deletebyuserid(Integer uid) {
        return articleMapper.deletebyuserid(uid);
    }
}
