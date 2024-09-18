package com.coding24.personalblog.mapper;


import com.coding24.personalblog.entity.Articlecomment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AcMapper {
    // 获取所有评论
    @Select("SELECT * FROM articlecomment")
    @Results({
            //为一个Articlecomment对象装配一个user对象,透过uid外键去联结
            @Result(property="user",column="uid",one=@One(select="com.coding24.personalblog.mapper.UserMapper.findUserById")),
            @Result(property = "uid", column = "uid")
    })
    List<Articlecomment> getAllComment();

    // 根据文章ID删除评论
    @Delete("DELETE FROM articlecomment WHERE aid = ${aid}")
    int deletebyarticleid(Integer aid);

    // 根据文章ID查询评论
    @Select("SELECT * FROM articlecomment WHERE aid=#{aid}")
    @Results({
            //为一个Articlecomment对象装配一个user对象,透过uid外键去联结
            @Result(property="user",column="uid",one=@One(select="com.coding24.personalblog.mapper.UserMapper.findUserById")),
            @Result(property = "uid", column = "uid")
    })
    List<Articlecomment> queryByArticleId(Integer aid);

    // 根据评论ID删除评论
    @Delete("DELETE FROM articlecomment WHERE acid = ${acid}")
    int deletebycommentid(Integer acid);

    //根据用户id删除评论
    @Delete("DELETE FROM articlecomment WHERE uid = ${uid}")
    int deletebyuserid(Integer uid);

    // 添加评论
    @Insert("INSERT INTO articlecomment(aid,articlecontent,uid) VALUES(#{aid}, #{articlecontent},#{uid})")
    int addcomment(Articlecomment articlecomment);

    // 根据评论ID查询文章ID
    @Select("select aid from articlecomment where acid=#{acid}")
    int queryArticleIdByid(Integer acid);
}
