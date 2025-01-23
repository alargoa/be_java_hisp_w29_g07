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
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    private List<Post> posts = new ArrayList<>();
    private static int idCounter = 1;

    public PostRepositoryImpl() throws IOException {
        loadPostsJson();
        posts.forEach(System.out::println);

        if (!posts.isEmpty()) {
            idCounter = posts.stream().mapToInt(Post::getId).max().orElse(0) + 1;
        }
    }

    @Override
    public List<Post> getPromoPostCount(Integer userId) {
        return posts.stream()
                .filter(post -> post.getUser_id().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Post savePost(Post post) {

        posts.add(post);

        return post;
    }

    @Override
    public Optional<Post> findPostById(Integer id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Post> saveAll() {
        return posts;
    }

    @Override
    public Integer findNextId() {
        return idCounter++;
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

}
