package com.coding24.personalblog.service;


import com.coding24.personalblog.entity.Article;
import com.coding24.personalblog.entity.User;
import com.coding24.personalblog.entity.UserRole;

import java.util.List;

public interface UsermanagementService {
    //添加新用户
    int add(User user);

    int addUR(UserRole userRole);

    User findUserByName(String username);

    List<User> getAllUsers();

    //更新用户
    int updateUser(User user);

    //通过用户ID查询用户详情
    User findUserById(Integer id);

    //删除用户
    int deleteUser(Integer id);

    int deleteUserRole(Integer id);
}
