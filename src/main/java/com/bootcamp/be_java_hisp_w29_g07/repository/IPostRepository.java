package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    List<Post> getPromoPostCount(Integer userId);

    Post savePost(Post post);

    Optional<Post> findPostById(Integer id);
    List<Post> saveAll();

    Integer findNextId();
}
