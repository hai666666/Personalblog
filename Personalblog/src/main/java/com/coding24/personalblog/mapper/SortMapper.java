package com.coding24.personalblog.mapper;

import com.coding24.personalblog.entity.Sort;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SortMapper {

    @Select("SELECT * FROM sort WHERE sid=#{sid}")
    Sort queryById(Integer sid);  //透过id查sort对象

    @Select("SELECT * FROM sort")
    List<Sort> getAllSort();  //取得所有sort对象
}
