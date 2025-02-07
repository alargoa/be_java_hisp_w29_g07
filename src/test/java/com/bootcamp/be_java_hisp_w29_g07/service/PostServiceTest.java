package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTOOut;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilPostFactory;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilUserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link PostServiceImpl}.
 * This class contains unit tests for the methods in {@link PostServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    /**
     * Mocked instance of {@link IUserService} to simulate user service operations.
     */
    @Mock
    private IUserService userService;

    /**
     * Mocked instance of {@link IPostRepository} to simulate post repository operations.
     */
    @Mock
    private IPostRepository postRepository;

    /**
     * Injected instance of {@link PostServiceImpl} with mocked dependencies.
     */
    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void addPost() {
    }

    @Test
    void findPostById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findListUsersFollowedPostsByUserId() {
    }

    @Test
    void findPromoPostCountByUserId() {
    }

    /**
     * Unit Test to verify that when a non-existent promotional post is created,
     * a PromoPostDTOOut is returned.
     */
    @Test
    void givenNonExistentPromoPost_whenCreatePromoPost_thenReturnPromoPostDTOOut() {
        User user = UtilUserFactory.getSeller("cmorales", 2);

        Post newPost = UtilPostFactory.getPromoPost();
        newPost.setId(null);

        Post savedPost = UtilPostFactory.getPromoPost();
        savedPost.setUserId(user.getId());
        PromoPostDTOOut promoPostDTOOutExpected = UtilPostFactory.getPromoPostDTOOut(savedPost);

        when(userService.findUserById(user.getId())).thenReturn(user);
        when(postRepository.savePost(newPost)).thenReturn(savedPost);

        PromoPostDTOOut promoPostDTOOutRes = postService.createPromoPost(UtilPostFactory.getPromoPostDTOIn(newPost));

        verify(userService).findUserById(user.getId());
        verify(postRepository).savePost(newPost);
        assertEquals(promoPostDTOOutExpected, promoPostDTOOutRes);
    }

    /**
     * Unit Test to verify that when a non-seller user attempts to create a promotional post,
     * a BadRequestException is thrown.
     */
    @Test
    void givenNonSellerUser_whenCreatePromoPost_thenReturnBadRequestException() {
        User user = UtilUserFactory.getUser("alargo", 1);

        Post newPost = UtilPostFactory.getPromoPost();
        newPost.setUserId(user.getId());
        newPost.setId(null);

        when(userService.findUserById(user.getId())).thenReturn(user);

        assertThrows(BadRequestException.class, () -> postService.createPromoPost(UtilPostFactory.getPromoPostDTOIn(newPost)));
        verify(userService).findUserById(user.getId());
    }

    /**
     * Unit Test to verify that when a new post without a promo is created,
     * a BadRequestException is thrown.
     */
    @Test
    void givenNewPostWithoutPromo_whenCreatePromoPost_thenReturnBadRequestException() {
        User user = UtilUserFactory.getSeller("cmorales", 2);

        Post newPost = UtilPostFactory.getPromoPost();
        newPost.setUserId(user.getId());
        newPost.setId(null);
        newPost.setHasPromo(false);

        when(userService.findUserById(user.getId())).thenReturn(user);

        assertThrows(BadRequestException.class, () -> postService.createPromoPost(UtilPostFactory.getPromoPostDTOIn(newPost)));
        verify(userService).findUserById(user.getId());
    }

    /**
     * Unit Test to verify that when a new post without a discount is created,
     * a BadRequestException is thrown.
     */
    @Test
    void givenNewPostWithoutDiscount_whenCreatePromoPost_thenReturnBadRequestException() {
        User user = UtilUserFactory.getSeller("cmorales", 2);

        Post newPost = UtilPostFactory.getPromoPost();
        newPost.setUserId(user.getId());
        newPost.setId(null);
        newPost.setDiscount(null);

        when(userService.findUserById(user.getId())).thenReturn(user);

        assertThrows(BadRequestException.class, () -> postService.createPromoPost(UtilPostFactory.getPromoPostDTOIn(newPost)));
        verify(userService).findUserById(user.getId());
    }

    /**
     * Unit Test to verify that when a new post with a discount of zero is created,
     * a BadRequestException is thrown.
     */
    @Test
    void givenNewPostWithDiscountZero_whenCreatePromoPost_thenReturnBadRequestException() {
        User user = UtilUserFactory.getSeller("cmorales", 2);

        Post newPost = UtilPostFactory.getPromoPost();
        newPost.setUserId(user.getId());
        newPost.setId(null);
        newPost.setDiscount(0.0);

        when(userService.findUserById(user.getId())).thenReturn(user);

        assertThrows(BadRequestException.class, () -> postService.createPromoPost(UtilPostFactory.getPromoPostDTOIn(newPost)));
        verify(userService).findUserById(user.getId());
    }

    @Test
    void findAllPostBySellerId() {
    }
}