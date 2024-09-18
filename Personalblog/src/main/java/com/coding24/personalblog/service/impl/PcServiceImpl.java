package com.coding24.personalblog.service.impl;

import com.coding24.personalblog.entity.Picturecomment;
import com.coding24.personalblog.mapper.PcMapper;
import com.coding24.personalblog.service.PcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcServiceImpl implements PcService {
    @Autowired
    PcMapper pcMapper;

    @Override
    public List<Picturecomment> getAllComment(){return pcMapper.getAllComment();}

    @Override
    public int deletebyPictureid(Integer pid){
        return pcMapper.deletebyPictureid(pid);
    }

    @Override
    public List<Picturecomment> queryByPictureId(Integer pid){return pcMapper.queryByPictureId(pid);}

    // 根据评论ID删除评论
    @Override
    public int deletebycommentid(Integer pcid){
        return pcMapper.deletebycommentid(pcid);
    }

    // 添加评论
    @Override
    public int addcomment(Picturecomment picturecomment){
        return pcMapper.addcomment(picturecomment);
    }

    // 根据评论ID查询文章ID
    @Override
    public int queryPictureIdByid(Integer pcid) {
        return pcMapper.queryPictureIdByid(pcid);
    }

    //根据用户id删除图片评论
    @Override
    public int deletebyuserid(Integer uid){
        return pcMapper.deletebyuserid(uid);
    }
}
