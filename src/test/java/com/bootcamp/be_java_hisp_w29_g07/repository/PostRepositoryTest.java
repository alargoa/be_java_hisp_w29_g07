package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilPostFactory;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class PostRepositoryTest {
    /**
     * Instance of {@link IPostRepository} that is being tested.
     * It is initialized with a concrete implementation before each test.
     */
    private IPostRepository postRepository;

    /**
     * Set up the test environment before each test method is run.
     * Initializes the {@link IPostRepository} with its concrete implementation {@link PostRepositoryImpl}.
     * This ensures that a fresh instance is created for each test.
     *
     */
    @BeforeEach
    public void setUp() throws IOException {
        this.postRepository = new PostRepositoryImpl();
        postRepository.deleteAll();
    }


    @Test
    void findPromoPostCountByUserId() {
    }

    @Test
    void savePost() {
    }

    @Test
    void findPostById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findNextId() {
    }

    /**
     * Unit Test to verify that when an existing user ID is provided,
     * the repository returns a list of posts from followed users created in the last two weeks.
     * The test asserts that the post list is not null, contains the expected number of posts,
     * and that each post's date is before the current date.
     */
    @Test
    void givenExistingUserId_whenFindPostsByUserIdsLastTwoWeeks_thenReturnPostList() {
        List<Integer> usersFollowing = new ArrayList<>();
        usersFollowing.add(1);
        usersFollowing.add(2);
        usersFollowing.add(3);
        Post postA = UtilPostFactory.getPostByUser(usersFollowing.get(0), 1);
        Post postB = UtilPostFactory.getPostByUser(usersFollowing.get(1), 1);
        Post postC = UtilPostFactory.getPostByUser(usersFollowing.get(2), 3);
        postRepository.savePost(postA);
        postRepository.savePost(postB);
        postRepository.savePost(postC);

        List<Post> postList = postRepository.findPostsByUserIdsAndLastTwoWeeks(usersFollowing);

        Assertions.assertNotNull(postList);
        Assertions.assertEquals(2, postList.size());
        assertThat(postList)
                .allSatisfy(post -> assertThat(post.getDate()).isBefore(LocalDate.now()));
    }

    /**
     * Unit Test to verify that when a non-existent user ID is provided,
     * the repository returns an empty list of posts from followed users created in the last two weeks.
     */
    @Test
    void givenNonExistentUserId_whenFindPostsByUserIdsLastTwoWeeks_thenReturnEmptyPostList() {
        List<Integer> usersFollowing = new ArrayList<>();
        usersFollowing.add(999);
        Post postA = UtilPostFactory.getPostByUser(1, 1);
        postRepository.savePost(postA);

        List<Post> postList = postRepository.findPostsByUserIdsAndLastTwoWeeks(usersFollowing);

        assertTrue(postList.isEmpty());
    }

    /**
     * Unit Test to verify that when existent user IDs are provided,
     * if none of the posts from these users fall within the last two weeks,
     * the repository returns an empty post list.
     */
    @Test
    void givenExistentUserId_whenFindPostsByUserIdsLastTwoWeeks_thenReturnPostList() {
        List<Integer> usersFollowing = new ArrayList<>();
        usersFollowing.add(1);
        usersFollowing.add(2);
        Post postA = UtilPostFactory.getPostByUser(usersFollowing.get(0), 2);
        Post postB = UtilPostFactory.getPostByUser(usersFollowing.get(1), 3);
        postRepository.savePost(postA);
        postRepository.savePost(postB);

        List<Post> postList = postRepository.findPostsByUserIdsAndLastTwoWeeks(usersFollowing);

        assertTrue(postList.isEmpty());
    }

    /**
     * Unit Test to verify that when existing posts are present for a given user ID,
     * the repository returns a list of posts for that user.
     * The test asserts that the actual list of posts matches the expected list of posts
     * in any order.
     */
    @Test
    void givenExistingPosts_whenFindAllPostsByUserId_thenReturnPostList() {
        List<Post> postsExpected = UtilPostFactory.createUnorderedPosts();
        postsExpected.forEach(p -> {
            p.setUserId(1);
            postRepository.savePost(p);
        });

        List<Post> postsActual = postRepository.findAllPostsByUserId(1);

        assertThat(postsActual).containsExactlyInAnyOrderElementsOf(postsExpected);
    }

    /**
     * Unit Test to verify that when no posts exist for a given user ID,
     * the repository returns an empty list of posts.
     * The test asserts that the actual list of posts is empty.
     */
    @Test
    void givenNonExistingPosts_whenFindAllPostsByUserId_thenReturnPostListEmpty() {
        List<Post> postsActual = postRepository.findAllPostsByUserId(1);

        assertTrue(postsActual.isEmpty());
    }
}