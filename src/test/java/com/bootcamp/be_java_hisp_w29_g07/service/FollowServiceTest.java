package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListFollowedDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.MessageDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilFollowFactory;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilUserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for {@link FollowServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    /**
     * Mocked instance of {@link IFollowRepository} used for testing the follow service without
     * interacting with the actual repository implementation.
     */
    @Mock
    private IFollowRepository followRepository;

    /**
     * Mocked instance of {@link IUserService} used for testing the follow service without
     * interacting with the actual user service.
     */
    @Mock
    private IUserService userService;

    /**
     * Instance of {@link FollowServiceImpl} with mocked dependencies injected for unit testing.
     * This is the service that is being tested.
     */
    @InjectMocks
    private FollowServiceImpl followService;

    @Test
    void getSellerFollowerCount() {
    }

    /**
     * Unit Test to verify that when the follow relationship is successfully deleted, the appropriate success message is returned.
     */
    @Test
    void givenExistingFollow_whenUnfollowUserById_thenReturnSuccessMessage() {
        Integer userIdFollower = 1;
        Integer userIdFollowed = 2;
        when(userService.verifyUserExists(userIdFollower)).thenReturn(true);
        when(userService.verifyUserExists(userIdFollowed)).thenReturn(true);
        when(followRepository.deleteFollowUserById(userIdFollower, userIdFollowed)).thenReturn(1);

        MessageDTO messageDTOResponse = followService.unfollowUserById(userIdFollower, userIdFollowed);

        assertNotNull(messageDTOResponse);
        assertEquals(String.format(Messages.USER_HAS_UNFOLLOWED_USER, userIdFollower, userIdFollowed), messageDTOResponse.getMessage());
        verify(userService).verifyUserExists(userIdFollower);
        verify(userService).verifyUserExists(userIdFollowed);
        verify(followRepository).deleteFollowUserById(userIdFollower, userIdFollowed);
    }

    /**
     * Unit Test to verify that when no follow relationship exists between the users, attempting to unfollow results in
     * a NotFoundException being thrown.
     */
    @Test
    void givenNonExistentFollow_whenDeleteFollowUserById_thenThrowsNotFoundException() {
        Integer userIdFollower = 1;
        Integer userIdFollowed = 2;
        when(userService.verifyUserExists(userIdFollower)).thenReturn(true);
        when(userService.verifyUserExists(userIdFollowed)).thenReturn(true);
        when(followRepository.deleteFollowUserById(userIdFollower, userIdFollowed)).thenReturn(0);

        assertThrows(NotFoundException.class, () -> followService.unfollowUserById(userIdFollower, userIdFollowed));
        verify(followRepository).deleteFollowUserById(userIdFollower, userIdFollowed);
        verify(userService).verifyUserExists(userIdFollower);
        verify(userService).verifyUserExists(userIdFollowed);
    }

    /**
     * Unit Test to verify that when the follower user does not exist,
     * attempting to unfollow results in a NotFoundException being thrown.
     */
    @Test
    void givenNonExistentUser_whenDeleteFollowUserById_thenThrowsNotFoundException() {
        Integer userIdFollower = 9999;
        Integer userIdFollowed = 2;
        when(userService.verifyUserExists(userIdFollower)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> followService.unfollowUserById(userIdFollower, userIdFollowed));
        verify(userService).verifyUserExists(userIdFollower);
    }

    @Test
    void saveFollow() {
    }

    /**
     * Unit Test to verify that when a user exists and is following other users,
     * the findListFollowedByUserId method returns the correct ListFollowedDTO
     * containing the followed users.
     */
    @Test
    void givenExistingUser_whenFindListFollowedByUserId_thenReturnListFollowedDTO() {
        User user1 = UtilUserFactory.getUser("steven", 1);
        User user2 = UtilUserFactory.getUser("pedro", 2);
        User user3 = UtilUserFactory.getUser("carlos", 3);
        when(userService.findUserById(1)).thenReturn(user1);


        Follow follow1 = UtilFollowFactory.getFollow(user1, user2);
        Follow follow2 = UtilFollowFactory.getFollow(user1, user3);

        List<Follow> followList = Arrays.asList(follow1, follow2);
        when(followRepository.findFollowedByUserId(user1.getId())).thenReturn(followList);

        ListFollowedDTO result = followService.findListFollowedByUserId(user1.getId(), "asc");

        assertNotNull(result);
        assertEquals(user1.getId(), result.getId());
        assertEquals("steven", result.getUserName());
        assertEquals(2, result.getFollowed().size());

        assertEquals(2, result.getFollowed().getFirst().getUserId());
        assertEquals("pedro", result.getFollowed().getFirst().getUserName());

        assertEquals(3, result.getFollowed().get(1).getUserId());
        assertEquals("carlos", result.getFollowed().get(1).getUserName());


        verify(userService).findUserById(user1.getId());
        verify(followRepository).findFollowedByUserId(user1.getId());

    }

    /**
     * Unit Test to verify that when an existing user has no followed users,
     * the findListFollowedByUserId method returns a ListFollowedDTO with an empty followed users list.
     */
    @Test
    void givenExistingUser_whenFindListFollowedByUserId_thenReturnEmptyListFollowedDTO() {
        User user1 = UtilUserFactory.getUser("steven", 1);
        when(userService.findUserById(1)).thenReturn(user1);
        ListFollowedDTO result = followService.findListFollowedByUserId(user1.getId(), "asc");
        assertNotNull(result);
        assertEquals(user1.getId(), result.getId());
        assertEquals("steven", result.getUserName());
        assertEquals(0, result.getFollowed().size());
    }

    @Test
    void givenNoExistingUser_whenFindListFollowedByUserId_thenReturnListFollowedDTO() {
        Integer userId = 99;
        when(userService.findUserById(userId)).thenThrow( NotFoundException.class);
        assertThrows(NotFoundException.class, () -> followService.findListFollowedByUserId(userId,"asc"));
        verify(userService).findUserById(userId);
    }


    @Test
    void findListFollowersByUserId() {
    }

    @Test
    void findFollowedByUserId() {
    }
}