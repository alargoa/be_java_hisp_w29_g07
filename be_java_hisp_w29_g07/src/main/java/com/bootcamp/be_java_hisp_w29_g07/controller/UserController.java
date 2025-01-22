package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.service.FollowServiceImpl;
import com.bootcamp.be_java_hisp_w29_g07.service.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;
    private final FollowServiceImpl followService;

    public UserController(UserServiceImpl userService, FollowServiceImpl followService) {
        this.userService = userService;
        this.followService = followService;
    }
}
