package com.bootcamp.be_java_hisp_w29_g07.integration;

import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import com.bootcamp.be_java_hisp_w29_g07.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
