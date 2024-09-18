package com.coding24.personalblog.service;

import com.coding24.personalblog.entity.Articlecomment;

import java.util.List;

public interface AcService {
    List<Articlecomment> getAllComment();
    int deletebyarticleid(Integer article_id);
    List<Articlecomment> queryByArticleId(Integer id);
    int deletebycommentid(Integer acid);
    int deletebyuserid(Integer uid);
    int addcomment(Articlecomment articlecomment);

    int queryArticleIdByid(Integer id);
}
