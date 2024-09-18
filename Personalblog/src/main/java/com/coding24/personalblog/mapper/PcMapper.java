package com.coding24.personalblog.mapper;

import com.coding24.personalblog.entity.Articlecomment;
import com.coding24.personalblog.entity.Picturecomment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PcMapper {
    // 获取所有评论
    @Select("SELECT * FROM picturecomment")
    @Results({
            //为一个Picturecomment对象装配一个user对象,透过uid外键去联结
            @Result(property="user",column="uid",one=@One(select="com.coding24.personalblog.mapper.UserMapper.findUserById")),
            @Result(property = "uid", column = "uid")
    })
    List<Picturecomment> getAllComment();

    // 根据ID删除评论
    @Delete("DELETE FROM picturecomment WHERE pid = ${pid}")
    int deletebyPictureid(Integer pid);

    // 根据图片ID查询评论
    @Select("SELECT * FROM picturecomment WHERE pid=#{pid}")
    @Results({
            //为一个Picturecomment对象装配一个user对象,透过uid外键去联结
            @Result(property="user",column="uid",one=@One(select="com.coding24.personalblog.mapper.UserMapper.findUserById")),
            @Result(property = "uid", column = "uid")
    })
    List<Picturecomment> queryByPictureId(Integer pid);

    // 根据评论ID删除评论
    @Delete("DELETE FROM picturecomment WHERE pcid = ${pcid}")
    int deletebycommentid(Integer pcid);

    // 添加评论
    @Insert("INSERT INTO picturecomment(pid,picturecontent,uid) VALUES(#{pid}, #{picturecontent},#{uid})")
    int addcomment(Picturecomment picturecomment);

    // 根据评论ID查询图片ID
    @Select("select pid from picturecomment where pcid=#{pcid}")
    int queryPictureIdByid(Integer pcid);

    //根据用户ID删除图片评论
    @Delete("DELETE FROM picturecomment WHERE uid = ${uid}")
    int deletebyuserid(Integer uid);
}
