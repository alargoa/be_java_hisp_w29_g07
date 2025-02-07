package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilPostFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private IPostRepository postRepository;

    @Mock
    private IUserService userService;

    @Mock
    private IFollowService followService;

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
     * Unit Test to verify that the service continues normally when the order type is valid.
     */
    @Test
    void givenValidOrder_whenFindListUsersFollowedPostsByUserId_thenContinueNormally() {
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
        verify(postRepository, times(1)).findPostsByUserIdsAndLastTwoWeeks(followedUserIds);
    }

    /**
     * Unit Test to verify that an exception is thrown when the order type is invalid.
     */
    @Test
    void givenInvalidOrder_whenFindListUsersFollowedPostsByUserId_thenThrowException() {
        Integer userId = 1;
        String invalidOrder = "random_order";
        assertThrows(
                BadRequestException.class,
                () -> postService.findListUsersFollowedPostsByUserId(userId, invalidOrder));
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