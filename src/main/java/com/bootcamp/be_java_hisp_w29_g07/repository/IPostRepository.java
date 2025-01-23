package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;

import java.util.List;

public interface IPostRepository {
    List<Post> getPromoPostCount(Integer userId);
}
