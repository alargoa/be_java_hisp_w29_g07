package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;

import java.util.List;

public interface IFollowRepository {
    List<Long> userFollow(Long userId);
    Long countByFollowedId(Integer userId);
}
