package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> getUserById(Integer userId);
}
