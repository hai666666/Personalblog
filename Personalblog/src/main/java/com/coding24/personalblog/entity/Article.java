package com.coding24.personalblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer aid;
    private String atitle;
    private String content;

    private List<Articlecomment> articlecomments;

    private Integer sid;
    private Sort sort;//分类实体

    private Integer uid;
    private User user;//用户实体
}
