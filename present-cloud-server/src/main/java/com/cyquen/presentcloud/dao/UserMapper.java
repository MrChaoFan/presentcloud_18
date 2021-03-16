package com.cyquen.presentcloud.dao;

import com.cyquen.presentcloud.entity.Role;
import com.cyquen.presentcloud.entity.UserDto;
import com.cyquen.presentcloud.security.CurrentUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    int deleteById(Integer id);

    int insert(UserDto u);

    UserDto selectById(@Param("id") Integer id);

    int updateById(UserDto user);

    UserDto selectByUsername(@Param("username") String username);

    CurrentUserDetails selectCurrentUserDetails(@Param("username")String username);

    List<Role> selectRolesById(Integer id);

    int updatePassword(@Param("password") String password,@Param("id") Integer id);
}
