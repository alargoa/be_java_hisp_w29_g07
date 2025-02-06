package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.MessageDTO;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void findListFollowedByUserId() {
    }

    @Test
    void findListFollowersByUserId() {
    }

    @Test
    void findFollowedByUserId() {
    }
}