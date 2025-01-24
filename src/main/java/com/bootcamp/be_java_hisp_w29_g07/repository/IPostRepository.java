package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    Long findPromoPostCountByUserId(Integer userId);
    Post savePost(Post post);
    Optional<Post> findPostById(Integer id);
    List<Post> findAll();
    Integer findNextId();
    List<Post> findPostsByUser(List<Integer> userFollowing);
}
