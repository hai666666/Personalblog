package com.coding24.personalblog.service.impl;

import com.coding24.personalblog.entity.Article;
import com.coding24.personalblog.entity.User;
import com.coding24.personalblog.entity.UserRole;
import com.coding24.personalblog.mapper.UserMapper;
import com.coding24.personalblog.mapper.UserRoleMapper;
import com.coding24.personalblog.service.UsermanagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsermanagementServiceImpl implements UsermanagementService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    //添加新用户
    @Override
    public int add(User user) {
        return userMapper.add(user);
    }

    @Override
    public int addUR(UserRole userRole) {
        return userRoleMapper.addUR(userRole);
    }

    @Override
    public User findUserByName(String username){return userMapper.findUserByName(username);}

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User findUserById(Integer id){
        return userMapper.findUserById(id);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public int deleteUserRole(Integer id){
        return userRoleMapper.deleteUserRole(id);
    }

}
