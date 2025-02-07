package com.bootcamp.be_java_hisp_w29_g07.integration;


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

/**
 * Integration test for the {@link PostController} controller.
 * This class contains integration tests for the endpoints in {@link PostController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerIntegrationTest {
    /**
     * Instance of {@link MockMvc} that allows simulating HTTP requests and verifying controller responses.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Static instance of {@link ObjectWriter} used for JSON serialization.
     */
    private static ObjectWriter writer;

    /**
     * Sets up the object writer for JSON serialization before each test.
     */
    @BeforeEach
    public void setUp() {
        writer = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer().withDefaultPrettyPrinter();
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
                        .content(writer.writeValueAsString(UtilPostFactory.getPromoPostDTOIn(newPost)))
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
}
