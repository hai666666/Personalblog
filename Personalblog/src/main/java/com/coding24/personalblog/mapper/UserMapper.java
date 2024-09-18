package com.coding24.personalblog.mapper;

import com.coding24.personalblog.entity.Article;
import com.coding24.personalblog.entity.Role;
import com.coding24.personalblog.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> getAllUsers();

    //根据姓名查找用户User
    @Select("select * from user where username=#{username}")
    User findUserByName(String username);
    //根据用户id查找角色Role集合
    @Select("select role.* from role,user_role where role.rid=user_role.rid and uid=#{uid}")
    List<Role> findRolesByUserId(Integer uid);

    //添加用户
    @Insert("INSERT INTO user(username,password) VALUES(#{username},#{password})")
    int add(User user);

    //更新用户
    @Update("UPDATE user SET username=#{username},password=#{password}  WHERE uid = #{uid}")
    int updateUser(User user);

    //根据uid查找用户User
    @Select("select * from user where uid=#{uid}")
    User findUserById(Integer id);

    //根据用户uid删除用户
    @Delete("DELETE FROM user WHERE uid = ${uid}")
    int deleteUser(Integer uid);
}