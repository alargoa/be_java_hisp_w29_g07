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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    /**
     * Mocked instance of {@link IPostRepository} used for testing the follow service without
     * interacting with the actual repository implementation.
     */
    @Mock
    private IPostRepository postRepository;

    /**
     * Mocked instance of {@link IUserService} used for testing the follow service without
     * interacting with the actual repository implementation.
     */
    @Mock
    private IUserService userService;

    /**
     * Mocked instance of {@link IFollowService} used for testing the follow service without
     * interacting with the actual repository implementation.
     */
    @Mock
    private IFollowService followService;

    /**
     * Instance of {@link PostServiceImpl} with mocked dependencies injected for unit testing.
     * This is the service that is being tested.
     */
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

    /**
     * Unit Test to verify that posts from followed users are returned in descending order by date.
     * <p>
     * This test mocks the retrieval of followed users and their posts.
     * It verifies that the posts are sorted in descending order (newest first).
     * </p>
     */
    @Test
    void givenExistingUnorderedPosts_whenFindListUsersFollowedPostsByUserId_thenReturnPostsOrderedByDateDesc() {
        Integer userId = 1;
        String order = "date_desc";
        List<Integer> followedUserIds = List.of(2, 4);
        List<Post> posts = UtilPostFactory.createUnorderedPosts();

        when(userService.verifyUserExists(userId)).thenReturn(true);
        when(followService.findFollowedByUserId(userId)).thenReturn(followedUserIds);
        when(postRepository.findPostsByUserIdsAndLastTwoWeeks(followedUserIds)).thenReturn(posts);

        ListPostDTO result = postService.findListUsersFollowedPostsByUserId(userId, order);

        assertNotNull(result);
        assertEquals(3, result.getPosts().size());
        assertTrue(result.getPosts().get(0).getDate().isAfter(result.getPosts().get(1).getDate()));
        assertTrue(result.getPosts().get(1).getDate().isAfter(result.getPosts().get(2).getDate()));

        verify(postRepository, times(1)).findPostsByUserIdsAndLastTwoWeeks(followedUserIds);

    }

    /**
     * Unit Test to verify that posts from followed users are returned in ascending order by date.
     * <p>
     * This test mocks the retrieval of followed users and their posts.
     * It verifies that the posts are sorted in ascending order (oldest first).
     * </p>
     */
    @Test
    void givenExistingUnorderedPosts_whenFindListUsersFollowedPostsByUserId_thenReturnPostsOrderedByDateAsc() {
        Integer userId = 1;
        String order = "date_asc";
        List<Integer> followedUserIds = List.of(2, 4);
        List<Post> posts = UtilPostFactory.createUnorderedPosts();

        when(userService.verifyUserExists(userId)).thenReturn(true);
        when(followService.findFollowedByUserId(userId)).thenReturn(followedUserIds);
        when(postRepository.findPostsByUserIdsAndLastTwoWeeks(followedUserIds)).thenReturn(posts);

        ListPostDTO result = postService.findListUsersFollowedPostsByUserId(userId, order);

        assertNotNull(result);
        assertEquals(3, result.getPosts().size());
        assertTrue(result.getPosts().get(0).getDate().isBefore(result.getPosts().get(1).getDate()));
        assertTrue(result.getPosts().get(1).getDate().isBefore(result.getPosts().get(2).getDate()));

        verify(postRepository, times(1)).findPostsByUserIdsAndLastTwoWeeks(followedUserIds);

    }

    /**
     * Unit Test to verify that when a valid user ID is provided,
     * the service returns a ListPostDTO containing the posts from followed users.
     * <p>
     * This test mocks the user existence check, retrieval of followed user IDs,
     * and fetching of posts from the repository, then asserts that the returned DTO
     * is not null and contains the expected posts.
     * </p>
     */
    @Test
    void givenExistingUserId_WhenFindListUsersFollowedPostsByUserId_ThenReturnPostsList() {
        Integer userId = 1;
        List<Integer> userFollowingIds = Arrays.asList(1, 2, 3);
        List<Post> posts = Arrays.asList(UtilPostFactory.getPostByUser(2, 1));
        when(userService.verifyUserExists(userId)).thenReturn(true);
        when(followService.findFollowedByUserId(userId)).thenReturn(userFollowingIds);
        when(postRepository.findPostsByUserIdsAndLastTwoWeeks(userFollowingIds)).thenReturn(posts);

        ListPostDTO postsDto = postService.findListUsersFollowedPostsByUserId(userId, null);

        assertNotNull(postsDto);
        assertEquals(posts.size(), postsDto.getPosts().size());
        verify(userService).verifyUserExists(userId);
        verify(followService).findFollowedByUserId(userId);
        verify(postRepository).findPostsByUserIdsAndLastTwoWeeks(userFollowingIds);
    }

    /**
     * Unit Test to verify that when a non-existent user ID is provided,
     * the service throws a NotFoundException while attempting to retrieve the list of followed posts.
     */
    @Test
    void givenNonExistentUserId_WhenFindListUsersFollowedPostsByUserId_ThenReturnNotFoundException() {
        Integer userId = 1;
        when(userService.verifyUserExists(userId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> postService.findListUsersFollowedPostsByUserId(userId, null));
        verify(userService).verifyUserExists(userId);
    }

    /**
     * Unit Test to verify that when a valid user exists but does not follow any users,
     * the service throws a NotFoundException while attempting to retrieve followed posts.
     */
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

    /**
     * Unit Test to verify that when no posts from followed users exist in the last two weeks,
     * the service throws a NotFoundException.
     */
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