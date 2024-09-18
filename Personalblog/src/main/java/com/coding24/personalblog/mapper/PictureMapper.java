package com.coding24.personalblog.mapper;

import com.coding24.personalblog.entity.Picture;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PictureMapper {
    // 根据图片ID查询图片信息
    @Select("SELECT * FROM picture WHERE pid=#{id}")
    @Results({
            //为一个Picture对象装配一个sort对象,透过sid外键去联结
            @Result(property="sort",column="sid",one=@One(select="com.coding24.personalblog.mapper.SortMapper.queryById")),
            @Result(property = "sid", column = "sid"),  //需再配置这行否则category的categoryId属性不出现
            //为一个Picture对象装配一个user对象,透过uid外键去联结
            @Result(property="user",column="uid",one=@One(select="com.coding24.personalblog.mapper.UserMapper.findUserById")),
            @Result(property = "uid", column = "uid")
    })
    Picture queryById(Integer id);

    // 获取所有图片信息
    @Select("SELECT * FROM picture")
    @Results({
            //为一个Picture对象装配一个sort对象,透过sid外键去联结
            @Result(property="sort",column="sid",one=@One(select="com.coding24.personalblog.mapper.SortMapper.queryById")),
            @Result(property = "sid", column = "sid"),  //需再配置这行否则category的categoryId属性不出现
            @Result(property="user",column="uid",one=@One(select="com.coding24.personalblog.mapper.UserMapper.findUserById")),
            @Result(property = "uid", column = "uid")
    })
    List<Picture> getAll();

    // 添加图片信息
    @Insert("INSERT INTO picture(ptitle,url,description,sid,uid) VALUES(#{ptitle}, #{url}, #{description}, #{sid}, #{uid})")
    @Options(useGeneratedKeys = true, keyProperty = "pid", keyColumn = "pid")
    int add(Picture picture);

    // 更新图片信息
    @Update("UPDATE picture SET ptitle=#{ptitle},url=#{url},description=#{description},sid=#{sid},uid=#{uid}  WHERE pid = #{pid}")
    int update(Picture picture);

    // 根据文章ID删除图片信息
    @Delete("DELETE FROM picture WHERE pid = ${pid}")
    int delete(Integer pid);

    // 根据用户ID删除图片信息
    @Delete("DELETE FROM picture WHERE uid = ${uid}")
    int deletebyuserid(Integer uid);

    // 根据用户ID查询图片信息
    @Select("SELECT * FROM picture WHERE uid=#{uid}")
    @Results({
            //为一个Picture对象装配一个sort对象,透过sid外键去联结
            @Result(property="sort",column="sid",one=@One(select="com.coding24.personalblog.mapper.SortMapper.queryById")),
            @Result(property = "sid", column = "sid"),  //需再配置这行否则category的categoryId属性不出现
            //为一个Picture对象装配一个user对象,透过uid外键去联结
            @Result(property="user",column="uid",one=@One(select="com.coding24.personalblog.mapper.UserMapper.findUserById")),
            @Result(property = "uid", column = "uid")
    })
    Picture queryByuserId(Integer id);
}
