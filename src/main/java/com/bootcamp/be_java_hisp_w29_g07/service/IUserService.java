package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.entity.User;

/**
 * The interface User service.
 */
public interface IUserService {
    /**
     * Find user by id user.
     *
     * @param userId the user id
     * @return the user
     */
    User findUserById(Integer userId);
}
