package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;

import java.util.List;

public interface IFollowRepository {
    Long countByFollowedId(Integer userId);
    List<User> iFollow(Integer userId);
}
