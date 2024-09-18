package com.coding24.personalblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer uid;  //用户编号
    private String username;
    private String password;
    private List<Role> roles;  //当前用户具有的角色,可有多个
}