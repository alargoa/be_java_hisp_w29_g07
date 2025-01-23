package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;

import java.util.List;
import java.util.Optional;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;

import java.util.List;

public interface IFollowRepository {

    Follow saveFollow(User user, User userToFollow);
    List<Follow> findAll();
    Optional<Follow> findFollow(User user, User userToFollow);
    Long countByFollowedId(Integer userId);
    List<User> iFollow(Integer userId);
}
