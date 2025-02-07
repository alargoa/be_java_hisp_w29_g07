package com.bootcamp.be_java_hisp_w29_g07.integration;

import com.bootcamp.be_java_hisp_w29_g07.controller.PostController;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilPostFactory;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilUserFactory;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for the {@link PostController} controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerIntegrationTest {
    /**
     * Instance of {@link MockMvc} that allows simulating HTTP requests and verifying controller responses.
     */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IFollowRepository followRepository;

    @BeforeEach
    void setUp() {
        followRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void givenExistingUserId_WhenFindListUsersFollowedPosts_ThenReturnSuccess() throws Exception {

        User userFollower = UtilUserFactory.getUser("gali", 1);
        User followedSellerA = UtilUserFactory.createUserSeller(2);
        User followedSellerB = UtilUserFactory.createUserSeller(4);

        followRepository.saveFollow(userFollower, followedSellerA);
        followRepository.saveFollow(userFollower, followedSellerB);

        List<Post> posts = UtilPostFactory.createUnorderedPosts();
        posts.forEach(post -> postRepository.savePost(post));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);
        LocalDate today = LocalDate.now();

        String responseContent = mockMvc.perform(get("/products/followed/{userId}/list", userFollower.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        List<String> dateStrings = JsonPath.read(responseContent, "$.posts[*].date");
        List<LocalDate> dates = dateStrings.stream()
                .map(dateStr -> LocalDate.parse(dateStr, formatter))
                .toList();

        dates.forEach(date -> {
            assertThat(date)
                    .as("The date %s should be between %s and %s", date, twoWeeksAgo, today)
                    .isAfterOrEqualTo(twoWeeksAgo)
                    .isBeforeOrEqualTo(today);
        });
    }

    /**
     * Integration Test to verify that posts from followed users are returned in descending order by date.
     * <p>
     * This test follows two seller users, creates posts for them, and then retrieves the posts.
     * It verifies that the posts are sorted in descending order (newest first).
     * </p>
     */
    @Test
        void givenExistingUnorderedPosts_whenFindListUsersFollowedPosts_thenReturnPostsOrderedByDateDesc() throws Exception {
        User userFollower = UtilUserFactory.getUser("user1", 1);
        User followedSeller1 = UtilUserFactory.createUserSeller(2);
        User followedSeller2 = UtilUserFactory.createUserSeller(4);
        String order = "date_desc";

        followRepository.saveFollow(userFollower, followedSeller1);
        followRepository.saveFollow(userFollower, followedSeller2);

        List<Post> posts = UtilPostFactory.createUnorderedPosts();
        posts.forEach(post -> postRepository.savePost(post));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        mockMvc.perform(get("/products/followed/{userId}/list", userFollower.getId())
                        .param("order", order))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts[0].date").isNotEmpty())
                .andExpect(jsonPath("$.posts[1].date").isNotEmpty())
                .andExpect(jsonPath("$.posts[2].date").isNotEmpty())
                .andDo(result -> {

                    String date1Str = JsonPath.read(result.getResponse().getContentAsString(), "$.posts[0].date");
                    String date2Str = JsonPath.read(result.getResponse().getContentAsString(), "$.posts[1].date");
                    String date3Str = JsonPath.read(result.getResponse().getContentAsString(), "$.posts[2].date");

                    LocalDate date1 = LocalDate.parse(date1Str, formatter);
                    LocalDate date2 = LocalDate.parse(date2Str, formatter);
                    LocalDate date3 = LocalDate.parse(date3Str, formatter);

                    assertTrue(date1.isAfter(date2));
                    assertTrue(date2.isAfter(date3));
                });
    }

    /**
     * Integration Test to verify that posts from followed users are returned in ascending order by date.
     * <p>
     * This test follows two seller users, creates posts for them, and then retrieves the posts.
     * It verifies that the posts are sorted in ascending order (oldest first).
     * </p>
     */
    @Test
    void givenExistingUnorderedPosts_whenFindListUsersFollowedPosts_thenReturnPostsOrderedByDateAsc() throws Exception {
        User userFollower = UtilUserFactory.getUser("user1", 1);
        User followedSeller1 = UtilUserFactory.createUserSeller(2);
        User followedSeller2 = UtilUserFactory.createUserSeller(4);
        String order = "date_asc";
        followRepository.saveFollow(userFollower, followedSeller1);
        followRepository.saveFollow(userFollower, followedSeller2);

        List<Post> posts = UtilPostFactory.createUnorderedPosts();
        posts.forEach(post -> postRepository.savePost(post));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        mockMvc.perform(get("/products/followed/{userId}/list", userFollower.getId())
                        .param("order", order))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts[0].date").isNotEmpty())
                .andExpect(jsonPath("$.posts[1].date").isNotEmpty())
                .andExpect(jsonPath("$.posts[2].date").isNotEmpty())
                .andDo(result -> {

                    String date1Str = JsonPath.read(result.getResponse().getContentAsString(), "$.posts[0].date");
                    String date2Str = JsonPath.read(result.getResponse().getContentAsString(), "$.posts[1].date");
                    String date3Str = JsonPath.read(result.getResponse().getContentAsString(), "$.posts[2].date");

                    LocalDate date1 = LocalDate.parse(date1Str, formatter);
                    LocalDate date2 = LocalDate.parse(date2Str, formatter);
                    LocalDate date3 = LocalDate.parse(date3Str, formatter);

                    assertTrue(date1.isBefore(date2));
                    assertTrue(date2.isBefore(date3));
                });
    }
}
