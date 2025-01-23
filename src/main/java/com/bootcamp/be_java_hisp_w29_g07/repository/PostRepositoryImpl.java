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
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements  IPostRepository{
    private List<Post> posts = new ArrayList<>();

    public PostRepositoryImpl() throws IOException {
        loadPostsJson();
        posts.forEach(System.out::println);
    }

    @Override
    public List<Post> getPromoPostCount(Integer userId) {
        return posts.stream()
                .filter(post -> post.getUserId().equals(userId))
                .collect(Collectors.toList());
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

    public List<Post> findPostByUser(List<Long> userFollowing, long userId) {
        List<Post> postsByUser = new ArrayList<>();

        for (Long id : userFollowing) {
            List<Post> filteredPosts = posts.stream()
                    .filter(post -> post.getUserId().equals(id))
                    .collect(Collectors.toList());

            // Agregas todos los Posts filtrados a la lista principal
            postsByUser.addAll(filteredPosts);
        }

        return postsByUser;
    }
}
