package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryImpl implements  IPostRepository{
    private List<Post> posts = new ArrayList<>();

    public PostRepositoryImpl() throws IOException {
        loadPostsJson();
        posts.forEach(System.out::println);
    }

    @Override
    public List<Post> getPromoPostCount(Integer userId) {
        return List.of();
    }

    private void loadPostsJson() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();
        List<Post> postsJson;

        file = ResourceUtils.getFile("classpath:posts.json");
        postsJson = objectMapper.readValue(file, new TypeReference<List<Post>>() {
        });

        posts = postsJson;
    }

    public List<Post> findPostByUser(long userId) {
        List<Post> postsByUser = posts.stream().filter(post -> post.)
    }
}
