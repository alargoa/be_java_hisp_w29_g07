package com.bootcamp.be_java_hisp_w29_g07.integration;

import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import com.bootcamp.be_java_hisp_w29_g07.constants.ValidationValues;
import com.bootcamp.be_java_hisp_w29_g07.controller.PostController;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTOOut;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilObjectMapper;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilPostFactory;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilUserFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for the {@link PostController} controller.
 * This class contains integration tests for the endpoints in {@link PostController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerIntegrationTest {
    
    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IFollowRepository followRepository;

    /**
     * Instance of {@link MockMvc} that allows simulating HTTP requests and verifying controller responses.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Sets up the object writer for JSON serialization before each test.
     */
    @BeforeEach
    public void setUp() {

        followRepository.deleteAll();
        postRepository.deleteAll();     
    }

    /**
     * Integration Test to verify that when a non-existent promotional post is created,
     * a success response entity is returned.
     */
    @Test
    public void givenNonExistentPromoPost_whenCreatePromoPost_thenReturnSuccessResponseEntity() throws Exception {
        User user = UtilUserFactory.getSeller("cmorales", 2);

        Post newPost = UtilPostFactory.getPromoPost();
        newPost.setId(null);

        Post savedPost = UtilPostFactory.getPromoPost();
        savedPost.setUserId(user.getId());
        PromoPostDTOOut promoPostDTOOutExpected = UtilPostFactory.getPromoPostDTOOut(savedPost);

        MvcResult res = mockMvc.perform(post("/products/promo-post")
                        .content(UtilObjectMapper.getObjectWriter()
                                .writeValueAsString(UtilPostFactory.getPromoPostDTOIn(newPost)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonRes = res.getResponse().getContentAsString();

        PromoPostDTOOut promoPostDTOOutRes = UtilObjectMapper.getObjectMapper().readValue(jsonRes, new TypeReference<PromoPostDTOOut>() {});
        promoPostDTOOutExpected.setPost_id(promoPostDTOOutRes.getPost_id());
        assertEquals(promoPostDTOOutExpected, promoPostDTOOutRes);
    }
        
    /**
     * Integration test to verify that posts from followed users are returned when no order parameter is provided.
     * <p>
     * This test follows two seller users, creates posts for them, and then retrieves the posts.
     * It verifies that the posts' dates fall within the last two weeks.
     * </p>
     */
    @Test
    public void givenExistingUserId_WhenFindListUsersFollowedPosts_ThenReturnSuccess() throws Exception {

        User userFollower = UtilUserFactory.getUser("gali", 1);
        User followedSellerA = UtilUserFactory.getSeller(2);
        User followedSellerB = UtilUserFactory.getSeller(4);

        followRepository.saveFollow(userFollower, followedSellerA);
        followRepository.saveFollow(userFollower, followedSellerB);

        List<Post> posts = UtilPostFactory.createUnorderedPosts();
        posts.forEach(post -> postRepository.savePost(post));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ValidationValues.DATE_PATTERN);
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
        User followedSeller1 = UtilUserFactory.getSeller(2);
        User followedSeller2 = UtilUserFactory.getSeller(4);
        String order = "date_desc";

        followRepository.saveFollow(userFollower, followedSeller1);
        followRepository.saveFollow(userFollower, followedSeller2);

        List<Post> posts = UtilPostFactory.createUnorderedPosts();
        posts.forEach(post -> postRepository.savePost(post));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ValidationValues.DATE_PATTERN);

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
        User followedSeller1 = UtilUserFactory.getSeller(2);
        User followedSeller2 = UtilUserFactory.getSeller(4);
        String order = "date_asc";
        followRepository.saveFollow(userFollower, followedSeller1);
        followRepository.saveFollow(userFollower, followedSeller2);

        List<Post> posts = UtilPostFactory.createUnorderedPosts();
        posts.forEach(post -> postRepository.savePost(post));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ValidationValues.DATE_PATTERN);

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

    /**
     * Integration Test to verify that an invalid order type returns a 400 Bad Request error.
     */
    @Test
    void givenInvalidOrder_whenFindListUsersFollowedPosts_thenReturnBadRequest() throws Exception {
        Integer userFollowerId = 1;
        String invalidOrder = "wrong_order";

        mockMvc.perform(get("/products/followed/{userId}/list", userFollowerId)
                        .param("order", invalidOrder))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(Messages.DATE_ORDER_INVALID));
    }

}
