package com.coding24.personalblog.service.impl;


import com.coding24.personalblog.entity.Picture;
import com.coding24.personalblog.mapper.PictureMapper;
import com.coding24.personalblog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    PictureMapper pictureMapper;

    @Override
    public Picture queryById(Integer id){return pictureMapper.queryById(id);}

    @Override
    public List<Picture> getAll() {
        return pictureMapper.getAll();
    }

    @Override
    public int add(Picture picture) {
        return pictureMapper.add(picture);
    }

    @Override
    public int update(Picture picture) {
        return pictureMapper.update(picture);
    }

    @Override
    public int delete(Integer id) {
        return pictureMapper.delete(id);
    }

    @Override
    public int deletebyuserid(Integer uid){
        return pictureMapper.deletebyuserid(uid);
    }

    @Override
    public Picture queryByuserId(Integer id){return pictureMapper.queryByuserId(id);}
}
