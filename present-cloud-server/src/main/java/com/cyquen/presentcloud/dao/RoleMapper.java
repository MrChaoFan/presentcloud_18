package com.cyquen.presentcloud.dao;

import com.cyquen.presentcloud.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    int deleteById(Integer id);

    int insert(Role e);

    Role selectById(Integer id);

    int updateById(Integer id);

    List<Role> selectAllRoles();
}
