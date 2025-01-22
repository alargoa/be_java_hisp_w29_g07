package com.bootcamp.be_java_hisp_w29_g07.repository;

public interface IFollowRepository {
    Long countByFollowedId(Integer userId);
}
