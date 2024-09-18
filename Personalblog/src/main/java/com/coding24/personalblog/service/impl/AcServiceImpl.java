package com.coding24.personalblog.service.impl;

import com.coding24.personalblog.entity.Articlecomment;
import com.coding24.personalblog.mapper.AcMapper;
import com.coding24.personalblog.service.AcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcServiceImpl implements AcService {
    @Autowired
    AcMapper acMapper;

    // 获取所有评论信息
    @Override
    public List<Articlecomment> getAllComment(){
        return acMapper.getAllComment();
    }

    // 根据文章ID删除评论
    @Override
    public int deletebyarticleid(Integer aid){
        return acMapper.deletebyarticleid(aid);
    }

    // 根据文章ID查询评论
    @Override
    public List<Articlecomment> queryByArticleId(Integer aid){return acMapper.queryByArticleId(aid);}

    // 根据评论ID删除评论
    @Override
    public int deletebycommentid(Integer acid){
        return acMapper.deletebycommentid(acid);
    }

    //根据用户ID删除评论
    @Override
    public int deletebyuserid(Integer uid){
        return acMapper.deletebyuserid(uid);
    }

    // 添加评论
    @Override
    public int addcomment(Articlecomment articlecomment){
        return acMapper.addcomment(articlecomment);
    }

    // 根据评论ID查询文章ID
    @Override
    public int queryArticleIdByid(Integer acid) {
        return acMapper.queryArticleIdByid(acid);
    }
}
