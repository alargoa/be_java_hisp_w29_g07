package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilPostFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private IPostRepository postRepository;

    @Mock
    private IFollowService followService;

    @Mock
    private  IUserService userService;

    @InjectMocks
    private PostServiceImpl postService;

    private ObjectMapper mapper;

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
    void givenExistingUserId_WhenFindListUsersFollowedPostsByUserId_ThenReturnPostsList() {
        // Given
        Integer userId = 1;
        List<Integer> userFollowingIds = Arrays.asList(1, 2, 3);
        List<Post> posts = Arrays.asList(UtilPostFactory.getPostByUser(2, 1));
        when(userService.verifyUserExists(userId)).thenReturn(true);
        when(followService.findFollowedByUserId(userId)).thenReturn(userFollowingIds);
        when(postRepository.findPostsByUserIdsAndLastTwoWeeks(userFollowingIds)).thenReturn(posts);
        // When

        ListPostDTO postsDto = postService.findListUsersFollowedPostsByUserId(userId, null);
        // Then
        assertNotNull(postsDto);
        assertEquals(posts.size(), postsDto.getPosts().size());
        verify(userService).verifyUserExists(userId);
        verify(followService).findFollowedByUserId(userId);
        verify(postRepository).findPostsByUserIdsAndLastTwoWeeks(userFollowingIds);
    }

    @Test
    void givenNonExistentUserId_WhenFindListUsersFollowedPostsByUserId_ThenReturnNotFoundException() {
        Integer userId = 1;
        when(userService.verifyUserExists(userId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> postService.findListUsersFollowedPostsByUserId(userId, null));
        verify(userService).verifyUserExists(userId);
    }

    @Test
    void givenNonExistentFollowedUserId_WhenFindListUsersFollowedPostsByUserId_ThenReturnNotFoundException() {
        Integer userId = 1;
        List<Integer> userFollowingIds = new ArrayList<>();
        when(userService.verifyUserExists(userId)).thenReturn(true);
        when(followService.findFollowedByUserId(userId)).thenReturn(userFollowingIds);

        assertThrows(NotFoundException.class, () -> postService.findListUsersFollowedPostsByUserId(userId, null));
        verify(userService).verifyUserExists(userId);
        verify(followService).findFollowedByUserId(userId);
    }

    @Test
    void givenNonExistentPostInLastTwoWeeks_WhenFindListUsersFollowedPostsByUserId_ThenReturnNotFoundException() {
        Integer userId = 1;
        List<Integer> userFollowingIds = Arrays.asList(1, 2, 3);
        List<Post> posts = new ArrayList<>();
        when(userService.verifyUserExists(userId)).thenReturn(true);
        when(followService.findFollowedByUserId(userId)).thenReturn(userFollowingIds);
        when(postRepository.findPostsByUserIdsAndLastTwoWeeks(userFollowingIds)).thenReturn(posts);

        assertThrows(NotFoundException.class, () -> postService.findListUsersFollowedPostsByUserId(userId, null));
        verify(userService).verifyUserExists(userId);
        verify(followService).findFollowedByUserId(userId);
        verify(postRepository).findPostsByUserIdsAndLastTwoWeeks(userFollowingIds);
    }

    @Test
    void findPromoPostCountByUserId() {
    }

    @Test
    void createPromoPost() {
    }

    @Test
    void findAllPostBySellerId() {
    }
}