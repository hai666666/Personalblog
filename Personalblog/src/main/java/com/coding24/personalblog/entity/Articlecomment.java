package com.coding24.personalblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Articlecomment {
    private Integer acid;
    private String articlecontent;
    private Integer aid;
    public void setArticleid(Integer aid) {
        this.aid = aid;
    }

    private Integer uid;
    private User user;//用户实体
}
