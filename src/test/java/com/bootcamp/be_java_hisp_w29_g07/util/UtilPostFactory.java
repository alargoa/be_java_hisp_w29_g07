package com.bootcamp.be_java_hisp_w29_g07.util;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Factory class for creating {@link Post}, {@link Product} and {@link PostDTO} objects with predefined values.
 * This class is used to simplify the creation of {@link Post} instances and their associated DTOs,
 * particularly in test scenarios where mock posts and products are required.
 */
public class UtilPostFactory {

    private static ObjectMapper mapper;

    /**
     * Creates a new {@link Post} instance for the given user and week offset.
     *
     * @param userId the ID of the user associated with the post.
     * @param week   the number of weeks to subtract from the current date to set the post's date.
     * @return a {@link Post} object with predefined values.
     */
    public static Post getPostByUser(Integer userId, Integer week) {
        return new Post(1, userId, LocalDate.now().minusWeeks(week), getProduct(userId + 1), 3, 100.0, false, 0.0);
    }

    /**
     * Creates a new {@link Product} instance with generic values.
     *
     * @param id the ID to assign to the product.
     * @return a {@link Product} object with generic attributes.
     */
    public static Product getProduct(Integer id) {
        return new Product(id, "Generic Product", "Generic Type", "Generic brand", "white", "N/A");
    }

    /**
     * Creates a list of {@link PostDTO} objects for the given user by converting {@link Post} instances.
     *
     * @param userId the ID of the user for which the posts are generated.
     * @return a list of {@link PostDTO} objects.
     */
    public static List<PostDTO> getPostDTOList(Integer userId) {
        List<Post> listPost = Arrays.asList(
                UtilPostFactory.getPostByUser(userId, 1),
                UtilPostFactory.getPostByUser(userId, 1)
        );

        return listPost.stream()
                .map(post -> getObjectMapper().convertValue(post, PostDTO.class))
                .toList();
    }

    public static PostDTO getPostDTO(Integer userId) {
        return getObjectMapper().convertValue(UtilPostFactory.getPostByUser(userId, 1), PostDTO.class);
    }

    /**
     * Creates a list of unordered {@link Post} instances.
     * This method is useful for testing scenarios where the order of posts is not predetermined.
     *
     * @return a list of {@link Post} objects.
     */
    public static List<Post> createUnorderedPosts() {
        return Arrays.asList(
                new Post(1, 4, LocalDate.now().minusDays(10), null, 101, 0.0, false, 0.0),
                new Post(2, 4, LocalDate.now().minusDays(2), null, 101, 0.0, false, 0.0),
                new Post(3, 2, LocalDate.now(), null, 101, 0.0, false, 0.0)
        );
    }

    /**
     * Returns a singleton instance of {@link ObjectMapper} configured with the {@link JavaTimeModule}.
     * If no instance exists, it creates and returns a new one.
     *
     * @return a configured {@link ObjectMapper}.
     */
    private static ObjectMapper getObjectMapper() {
        if (Objects.isNull(mapper)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.findAndRegisterModules();
            return mapper;
        }
        return mapper;
    }
}

