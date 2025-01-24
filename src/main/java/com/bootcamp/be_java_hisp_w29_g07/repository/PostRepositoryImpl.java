package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
    }

    @Override
    public Long findPromoPostCountByUserId(Integer userId) {
        return (long) posts.stream()
                .filter(post -> post.getUser_id().equals(userId))
                .filter(Post::getHasPromo)
                .toList()
                .size();
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
    public List<Post> findAll() {
        return posts;
    }

    @Override
    public Integer findNextId() {
        return idCounter++;
    }

    @Override
    public List<Post> findPostsByUserIdsAndLastTwoWeeks(List<Integer> userFollowing) {
        return posts.stream().filter(post -> userFollowing.contains(post.getUser_id()) &&
                        post.getDate().isAfter(LocalDate.now().minusWeeks(2)))
                .toList();
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
        if (!posts.isEmpty()) {
            idCounter = posts.stream().mapToInt(Post::getId).max().orElse(0) + 1;
        }
    }
}
