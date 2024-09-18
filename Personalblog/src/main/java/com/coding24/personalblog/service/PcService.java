package com.coding24.personalblog.service;

import com.coding24.personalblog.entity.Picturecomment;

import java.util.List;

public interface PcService {
    List<Picturecomment> getAllComment();
    int deletebyPictureid(Integer pid);
    List<Picturecomment> queryByPictureId(Integer pid);
    int deletebycommentid(Integer pcid);
    int addcomment(Picturecomment picturecomment);

    int queryPictureIdByid(Integer id);

    int deletebyuserid(Integer uid);
}

