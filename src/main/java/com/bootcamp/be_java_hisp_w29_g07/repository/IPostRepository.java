package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.dto.PromoPostDto;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    List<Post> getPromoPostCount(Integer userId);

    Post addPost(Post post);

    Optional<Post> findPostById(Integer id);
    List<Post> getAll();

    Integer getNextId();
}
