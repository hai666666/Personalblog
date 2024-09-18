package com.coding24.personalblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    private Integer pid;
    private String ptitle;
    private String url;
    private String description;//图片描述
    private List<Picturecomment> picturecomments;

    private Integer sid;
    private Sort sort;//分类实体

    private Integer uid;
    private User user;//用户实体
}
