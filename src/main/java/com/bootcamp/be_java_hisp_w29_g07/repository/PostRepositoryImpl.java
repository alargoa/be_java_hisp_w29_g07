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

/**
 * The type Post repository.
 */
@Repository
public class PostRepositoryImpl implements IPostRepository {
    /**
     * The Posts.
     */
    private List<Post> posts = new ArrayList<>();
    /**
     * The constant idCounter.
     */
    private static int idCounter = 1;

    /**
     * Instantiates a new Post repository.
     *
     * @throws IOException the io exception
     */
    public PostRepositoryImpl() throws IOException {
        loadPostsJson();
    }

    /**
     * Find promo post count by user id long.
     *
     * @param userId the user id
     * @return the long
     */
    @Override
    public Long findPromoPostCountByUserId(Integer userId) {
        return (long) posts.stream()
                .filter(post -> post.getUserId().equals(userId))
                .filter(Post::getHasPromo)
                .toList()
                .size();
    }

    /**
     * Save post post.
     *
     * @param post the post
     * @return the post
     */
    @Override
    public Post savePost(Post post) {
        post.setId(findNextId());
        posts.add(post);
        return post;
    }

    /**
     * Find post by id optional.
     *
     * @param id the id
     * @return the optional
     */
    @Override
    public Optional<Post> findPostById(Integer id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<Post> findAll() {
        return posts;
    }

    /**
     * Find next id integer.
     *
     * @return the integer
     */
    @Override
    public Integer findNextId() {
        return idCounter++;
    }

    /**
     * Find posts by user ids and last two weeks list.
     *
     * @param userFollowing the user following
     * @return the list
     */
    @Override
    public List<Post> findPostsByUserIdsAndLastTwoWeeks(List<Integer> userFollowing) {
        return posts.stream().filter(post -> userFollowing.contains(post.getUserId()) &&
                        post.getDate().isAfter(LocalDate.now().minusWeeks(2)))
                .toList();
    }

    /**
     * Load posts json.
     *
     * @throws IOException the io exception
     */
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
