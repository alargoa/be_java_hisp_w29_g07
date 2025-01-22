package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryImpl implements  IPostRepository{
    private List<Post> posts = new ArrayList<>();

    public PostRepositoryImpl(List<Post> posts) {
        // TO-DO: Build json file
        this.posts = posts;
    }

    @Override
    public List<Post> getPromoPostCount(Integer userId) {
        return List.of();
    }
}
