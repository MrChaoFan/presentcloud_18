package com.cyquen.presentcloud.controller;

import com.cyquen.presentcloud.entity.UserDto;
import com.cyquen.presentcloud.security.CurrentUserDetails;
import com.cyquen.presentcloud.security.crypto.aes.SecurityParameter;
import com.cyquen.presentcloud.service.UserDetailsServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zhengxikun
 */
@RestController
public class UserController {

    private UserDetailsServiceImpl userService;

    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user/info")
    public UserDto queryUserInfo(@AuthenticationPrincipal CurrentUserDetails user) {
        return userService.getUserInfo(user.getId());
    }

    @SecurityParameter
    @GetMapping("/test")
    public String test(@RequestParam("hello") String hello) {
        System.out.println(hello);
        return "test";
    }

}
