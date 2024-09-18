package com.coding24.personalblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Integer rid;  //角色编号
    private String rolename;  //角色名称
    private String description;  //角色描述或中文名
}