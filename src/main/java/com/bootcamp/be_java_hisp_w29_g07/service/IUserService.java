package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.entity.User;

public interface IUserService {
    User findUser(Integer userId);
}
