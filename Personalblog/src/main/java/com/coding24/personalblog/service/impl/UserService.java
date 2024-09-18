package com.coding24.personalblog.service.impl;

import com.coding24.personalblog.entity.Role;
import com.coding24.personalblog.entity.User;
import com.coding24.personalblog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        User user=userMapper.findUserByName(username);//从数据库中查询用户
        if(user==null){
            throw new UsernameNotFoundException("帐户不存在");
        }
        user.setRoles(userMapper.findRolesByUserId(user.getUid()));//从数据库中查询到该用户的所有角色（含权限）
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role:user.getRoles()){//取出用户的角色，封装到authorities中
            authorities.add(new SimpleGrantedAuthority(role.getRolename()));
        }
        //下面的密码一般要加密
        return new
                org.springframework.security.core.userdetails.User(user.getUsername(),new
                BCryptPasswordEncoder().encode(user.getPassword()),authorities);
    }
}