package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilPostFactory;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.*;
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

    @Test
    void givenNonExistentUserId_whenFindPostsByUserIdsLastTwoWeeks_thenReturnEmptyPostList() {
        List<Integer> usersFollowing = new ArrayList<>();
        usersFollowing.add(999);
        Post postA = UtilPostFactory.getPostByUser(1, 1);
        postRepository.savePost(postA);

        List<Post> postList = postRepository.findPostsByUserIdsAndLastTwoWeeks(usersFollowing);

        Assertions.assertTrue(postList.isEmpty());
    }

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

        Assertions.assertTrue(postList.isEmpty());
    }

    @Test
    void findAllPostsByUserId() {
    }
}