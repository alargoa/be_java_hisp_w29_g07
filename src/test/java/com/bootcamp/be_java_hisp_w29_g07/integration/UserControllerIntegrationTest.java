package com.bootcamp.be_java_hisp_w29_g07.integration;

import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import com.bootcamp.be_java_hisp_w29_g07.controller.UserController;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for the {@link UserController} controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    /**
     * Instance of {@link MockMvc} that allows simulating HTTP requests and verifying controller responses.
     */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IFollowRepository followRepository;

    @BeforeEach
    void setUp() {
        followRepository.deleteAll();
    }

    /**
     * Integration Test to verify that a user can unfollow another user when the follower is already following the target user.
     * <p>
     * This test simulates a user unfollowing another user when the follow relationship exists.
     * It first performs a "follow" operation and then an "unfollow" operation.
     * It verifies that the response is a success message indicating that the user has successfully unfollowed.
     * </p>
     */
    @Test
    public void givenExistingFollow_whenUnfollowUserById_thenReturnSuccess() throws Exception {
        Integer userIdFollower = 1;
        Integer userIdToFollow = 2;

        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", userIdFollower, userIdToFollow))
                .andDo(print())
                .andExpect(status().isOk());


        MvcResult res = mockMvc.perform(
                        post("/users/{userId}/unfollow/{userIdToUnfollow}", userIdFollower, userIdToFollow))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonRes = res.getResponse().getContentAsString();

        assertTrue(jsonRes.contains(String.format(Messages.USER_HAS_UNFOLLOWED_USER, userIdFollower, userIdToFollow)));
    }

    /**
     * Integration Test to verify that an attempt to unfollow a user when no follow relationship exists
     * results in a 404 Not Found error.
     * <p>
     * This test simulates an attempt to unfollow a user when the follower is not following the target user.
     * It verifies that the system returns a 404 Not Found error with the appropriate error message.
     * </p>
     */
    @Test
    public void givenNonExistentFollow_whenUnfollowUserById_thenReturnNotFoundError() throws Exception {
        Integer userIdFollower = 1;
        Integer userIdToFollow = 2;

        MvcResult res = mockMvc.perform(
                        post("/users/{userId}/unfollow/{userIdToUnfollow}", userIdFollower, userIdToFollow))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonRes = res.getResponse().getContentAsString();
        assertTrue(jsonRes.contains(String.format(Messages.USER_IS_NOT_FOLLOWING_USER, userIdFollower, userIdToFollow)));
    }


    /**
     * Integration Test to verify that an attempt to unfollow a user when the follower does not exist
     * results in a 404 Not Found error.
     * <p>
     * This test simulates an attempt to unfollow a user where the follower does not exist in the system.
     * It verifies that the system returns a 404 Not Found error with the appropriate error message.
     * </p>
     */
    @Test
    public void givenNonExistentUser_whenUnfollowUserById_thenReturnNotFoundError() throws Exception {
        Integer userIdFollower = 9999;
        Integer userIdToFollow = 2;

        MvcResult res = mockMvc.perform(
                        post("/users/{userId}/unfollow/{userIdToUnfollow}", userIdFollower, userIdToFollow))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonRes = res.getResponse().getContentAsString();
        assertTrue(jsonRes.contains(String.format(Messages.USER_NOT_FOUND_MSG, userIdFollower)));
    }

    /**
     * Integration Test to verify that a seller with followers returns the correct follower count.
     * <p>
     * This test simulates users following a seller and then retrieves the follower count.
     * It verifies that the response contains the correct number of followers and the seller's user ID.
     * </p>
     */
    @Test
    void givenExistentSeller_whenGetSellerFollowerCount_thenReturnCorrectCountResponse() throws Exception {
        Integer userFollowerId1 = 1;
        Integer userFollowerId2= 3;
        Integer sellerFollowedId = 2;
        Long expectedFollowers = 2L;
        //Follow
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", userFollowerId1, sellerFollowedId))
                .andExpect(status().isOk());

        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", userFollowerId2, sellerFollowedId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/{userId}/followers/count", sellerFollowedId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.followers_count").value(expectedFollowers))
                .andExpect(jsonPath("$.user_id").value(sellerFollowedId));
    }


    /**
     * Integration Test to verify that a seller with no followers returns a count of zero.
     * <p>
     * This test retrieves the follower count for a seller who has no followers.
     * It verifies that the response correctly returns zero followers.
     * </p>
     */
    @Test
    void givenExistentSellerWithNoFollowers_whenGetSellerFollowerCount_thenReturnZeroCountResponse() throws Exception {
        Integer sellerFollowedId = 4;
        Long expectedFollowers = 0L;

        mockMvc.perform(get("/users/{userId}/followers/count", sellerFollowedId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.followers_count").value(expectedFollowers))
                .andExpect(jsonPath("$.user_id").value(sellerFollowedId));
    }

    /**
     * Integration Test to verify that retrieving follower count for a non-existent seller returns a 404 error.
     * <p>
     * This test attempts to retrieve the follower count for a seller that does not exist.
     * It verifies that the system returns a 404 Not Found error with the expected message.
     * </p>
     */
    @Test
    void givenNonExistentSeller_whenGetSellerFollowerCount_thenReturnNotFoundError() throws Exception {

        Integer sellerFollowedId = 99;
        String expectedMsg = String.format(Messages.USER_NOT_FOUND_MSG, sellerFollowedId);

        mockMvc.perform(get("/users/{userId}/followers/count", sellerFollowedId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMsg));
    }


    /**
     * Integration Test to verify that retrieving follower count for a non-seller user returns a 400 error.
     * <p>
     * This test attempts to retrieve the follower count for a user that is not a seller.
     * It verifies that the system returns a 400 Bad Request error with the expected message.
     * </p>
     */
    @Test
    void givenNonSellerUserId_whenGetSellerFollowerCount_thenReturnUserNotSellerError() throws Exception {
        Integer sellerFollowedId = 1;
        String expectedMsg = Messages.USER_NOT_SELLER_MSG;

        mockMvc.perform(get("/users/{userId}/followers/count", sellerFollowedId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMsg));
    }


    /**
     * Integration Test to verify that an attempt to follow a user when both users exist
     * results in a successful follow operation.
     * <p>
     * This test simulates an attempt to follow another user when both the follower and the target user
     * exist in the system. It verifies that the system returns a success status with a message indicating
     * that the follower successfully followed the target user.
     * </p>
     */
    @Test
    void givenExistUser_whenUnfollowUserById_thenReturnNotFoundError() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/users/{userId}/follow/{userIdToFollow}", 5,4))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User Brayan Camilo follows user Juan Steven"));
    }

    /**
     * Integration Test to verify that an attempt to follow a user when the follower does not exist
     * results in a 404 Not Found error.
     * <p>
     * This test simulates an attempt to follow a user when the follower does not exist in the system.
     * It verifies that the system returns a 404 Not Found error with the appropriate error message,
     * indicating that the follower with the given id could not be found.
     * </p>
     */
    @Test
    void givenNotExistUser_whenUnfollowUserById_thenReturnNotFoundError() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/{userId}/follow/{userIdToFollow}", 115,2))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with id 115 not found"));
    }

    /**
     * Integration Test to verify that an attempt to follow a user when the follower id is invalid
     * results in a 400 Bad Request error.
     * <p>
     * This test simulates an attempt to follow a user when the provided follower id is invalid (non-numeric).
     * It verifies that the system returns a 400 Bad Request error, indicating that the request is malformed.
     * </p>
     */
    @Test
    void given_whenUnfollowUserById_thenReturnNotFoundError() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/{userId}/follow/{userIdToFollow}", "a",2))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
