package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilUserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test class for {@link FollowRepositoryImpl}.
 */
class FollowRepositoryTest {

    /**
     * Instance of {@link IFollowRepository} that is being tested.
     * It is initialized with a concrete implementation before each test.
     */
    private IFollowRepository followRepository;

    /**
     * Set up the test environment before each test method is run.
     * Initializes the {@link IFollowRepository} with its concrete implementation {@link FollowRepositoryImpl}.
     * This ensures that a fresh instance is created for each test.
     *
     */
    @BeforeEach
    public void setUp() {
        this.followRepository = new FollowRepositoryImpl();
    }

    @Test
    void saveFollow() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findFollow() {
    }

    @Test
    void countByFollowedId() {
    }

    @Test
    void findFollowedByUserId() {
    }

    @Test
    void findFollowersByUserId() {
    }

    /**
     * Unit Test to verify that when a follow relationship exists between two users,
     * deleting the follow by user ID returns 1 and the follow no longer exists.
     */
    @Test
    void givenExistingFollow_whenDeleteFollowUserById_thenReturnOne() {
        User userFollower = UtilUserFactory.getUser("alargo", 6);
        User userFollowed = UtilUserFactory.getUser("jfeo", 7);
        followRepository.saveFollow(userFollower, userFollowed);

        Integer numberOfFollowsDeleted = followRepository.deleteFollowUserById(userFollower.getId(), userFollowed.getId());

        Assertions.assertEquals(1, numberOfFollowsDeleted);
        Assertions.assertTrue(followRepository.findFollow(userFollower, userFollowed).isEmpty());
    }

    /**
     * Unit Test to verify that when no follow relationship exists between two id users,
     * using one existing user ID and one non-existent user ID, the operation
     * returns 0, indicating no follow was deleted.
     */
    @Test
    void givenNonExistentFollowByOneExistentId_whenDeleteFollowUserById_thenReturnZero() {
        User userFollower = UtilUserFactory.getUser("alargo", 6);
        User userFollowed = UtilUserFactory.getUser("jfeo", 7);
        followRepository.saveFollow(userFollower, userFollowed);

        Integer numberOfFollowsDeleted = followRepository.deleteFollowUserById(9, userFollowed.getId());

        Assertions.assertEquals(0, numberOfFollowsDeleted);
    }

    /**
     * Unit Test to verify that when no follow relationship exists between two id users,
     * attempting to delete the follow by user ID returns 0.
     */
    @Test
    void givenNonExistentFollow_whenDeleteFollowUserById_thenReturnZero() {
        Integer numberOfFollowsDeleted = followRepository.deleteFollowUserById(6, 7);

        Assertions.assertEquals(0, numberOfFollowsDeleted);
    }
}