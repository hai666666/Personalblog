package com.coding24.personalblog.service;

import com.coding24.personalblog.entity.Picture;

import java.util.List;

public interface PictureService {
    Picture queryById(Integer id);

    List<Picture> getAll();

    int add(Picture picture);

    int update(Picture picture);

    int delete(Integer id);

    int deletebyuserid(Integer uid);

    Picture queryByuserId(Integer id);
}
