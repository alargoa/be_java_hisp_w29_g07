package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.User;

import java.util.Optional;

/**
 * The interface User repository.
 */
public interface IUserRepository {
    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    Optional<User> getUserById(Integer userId);
    boolean existsById(Integer userId);
}
