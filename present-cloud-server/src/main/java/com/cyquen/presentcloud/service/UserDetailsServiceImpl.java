package com.cyquen.presentcloud.service;

import com.cyquen.presentcloud.dao.UserMapper;
import com.cyquen.presentcloud.entity.Role;
import com.cyquen.presentcloud.entity.UserDto;
import com.cyquen.presentcloud.security.CurrentUserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zhengxikun
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final static Log logger = LogFactory.getLog(UserDetailsServiceImpl.class);

    private UserMapper userMapper;

    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Cacheable(value = "userDetails", key = "#username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CurrentUserDetails u = userMapper.selectCurrentUserDetails(username);
        if(u == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        u.setRoles(userMapper.selectRolesById(u.getId()));
        return u;
    }

    public List<Role> getRolesByUserId(Integer userId) {
        return userMapper.selectRolesById(userId);
    }

    public UserDto getUserInfo(Integer userId) {
        UserDto userDto = userMapper.selectById(userId);
        List<Role> roles = userMapper.selectRolesById(userId);
        userDto.setRoles(roles.stream().map(Role::getName).collect(Collectors.toList()));
        return userDto;
    }
}
