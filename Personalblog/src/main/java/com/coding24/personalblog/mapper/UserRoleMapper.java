package com.coding24.personalblog.mapper;

import com.coding24.personalblog.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    @Insert("INSERT INTO user_role(uid,rid) VALUES(#{uid},#{rid})")
    int addUR(UserRole userRole);

    //根据用户uid删除
    @Delete("DELETE FROM user_role WHERE uid = ${uid}")
    int deleteUserRole(Integer uid);
}
