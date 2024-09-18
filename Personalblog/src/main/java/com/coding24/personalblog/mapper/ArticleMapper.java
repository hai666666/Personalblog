package com.coding24.personalblog.mapper;

import com.coding24.personalblog.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    // 根据文章ID查询文章信息
    @Select("SELECT * FROM article WHERE aid=#{aid}")
    @Results({
            //为一个Article对象装配一个sort对象,透过sid外键去联结
            @Result(property="sort",column="sid",one=@One(select="com.coding24.personalblog.mapper.SortMapper.queryById")),
            @Result(property = "sid", column = "sid"), //需再配置这行否则sort的sortId属性不出现
            //为一个Article对象装配一个user对象,透过uid外键去联结
            @Result(property="user",column="uid",one=@One(select="com.coding24.personalblog.mapper.UserMapper.findUserById")),
            @Result(property = "uid", column = "uid")
    })
    Article queryById(Integer id);

    // 获取所有文章信息
    @Select("SELECT * FROM article")
    @Results({
            //为一个Article对象装配一个sort对象,透过sid外键去联结
            @Result(property="sort",column="sid",one=@One(select="com.coding24.personalblog.mapper.SortMapper.queryById")),
            @Result(property = "sid", column = "sid"), //需再配置这行否则category的categoryId属性不出现
            //为一个Article对象装配一个user对象,透过uid外键去联结
            @Result(property="user",column="uid",one=@One(select="com.coding24.personalblog.mapper.UserMapper.findUserById")),
            @Result(property = "uid", column = "uid")
    })
    List<Article> getAll();

    // 添加文章信息
    @Insert("INSERT INTO article(atitle,content,sid,uid) VALUES(#{atitle}, #{content}, #{sid}, #{uid})")
    @Options(useGeneratedKeys = true, keyProperty = "aid", keyColumn = "aid")
    int add(Article article);

    // 更新文章信息
    @Update("UPDATE article SET atitle=#{atitle},content=#{content},sid=#{sid},uid=#{uid}  WHERE aid = #{aid}")
    int update(Article article);

    // 根据文章ID删除文章信息
    @Delete("DELETE FROM article WHERE aid = ${aid}")
    int delete(Integer aid);

    //根据用户ID删除文章信息
    @Delete("DELETE FROM article WHERE uid = ${uid}")
    int deletebyuserid(Integer uid);

}
