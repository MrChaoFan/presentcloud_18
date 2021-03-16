package com.cyquen.presentcloud.dao;

import com.cyquen.presentcloud.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {

    int deleteByPrimaryKey(Integer userId, Integer roleId);

    int insert(UserRole ur);

    int insertMutiRole(Integer userId, Integer[] roleIds);

    int update(UserRole ur);

    int deleteByUserId(Integer userId);
}
