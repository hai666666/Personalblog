package com.coding24.personalblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picturecomment {
    private Integer pcid;
    private String picturecontent;
    private Integer pid;
    public void setPictureid(Integer pid) {this.pid = pid;}

    private Integer uid;
    private User user;//用户实体
}
