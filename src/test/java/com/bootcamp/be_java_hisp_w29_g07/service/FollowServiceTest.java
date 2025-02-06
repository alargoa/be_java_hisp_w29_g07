package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.MessageDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilFollowFactory;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilUserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    /**
     * Unit Test to verify that when an existing user attempts to follow a seller,
     * a success message is returned indicating the user has followed the seller.
     */
    @Test
    void givenExistingUserAndSeller_whenSaveFollow_thenReturnMessageDTO() {
        User user = UtilUserFactory.getUser("alargo",20);
        User userToFollow = UtilUserFactory.getUser("cmorales",21);
        userToFollow.setUserType(UserType.SELLER);
        Follow follow = UtilFollowFactory.getFollow(user, userToFollow);
        when(followRepository.saveFollow(user, userToFollow)).thenReturn(follow);
        when(userService.findUserById(user.getId())).thenReturn(user);
        when(userService.findUserById(userToFollow.getId())).thenReturn(userToFollow);

        MessageDTO messageResponse = followService.saveFollow(user.getId(), userToFollow.getId());

        assertEquals(String.format(Messages.USER_FOLLOW_SELLER, user.getName(), userToFollow.getName()), messageResponse.getMessage());
    }

    /**
     * Unit Test to verify that when both the user and the user to follow are sellers,
     * a BadRequestException is thrown, as a seller cannot follow another seller.
     */
    @Test
    void givenSellerFollowSeller_whenSaveFollow_thenThrowsBadRequestException(){
        User user = UtilUserFactory.getUser("alargo",11);
        User userToFollow = UtilUserFactory.getUser("cmorales",12);
        user.setUserType(UserType.SELLER);
        userToFollow.setUserType(UserType.SELLER);
        when(userService.findUserById(user.getId())).thenReturn(user);
        when(userService.findUserById(userToFollow.getId())).thenReturn(userToFollow);

        assertThrows(BadRequestException.class, () -> followService.saveFollow(user.getId(), userToFollow.getId()));
        verify(userService, atLeastOnce()).findUserById(user.getId());
        verify(userService, atLeastOnce()).findUserById(userToFollow.getId());
    }

    /**
     * Unit Test to verify that when a non-existent user tries to follow another user,
     * a NotFoundException is thrown indicating the user cannot be found.
     */
    @Test
    void givenNonExistentUser_whenSaveFollow_thenThrowsNotFoundException() {
        User user = UtilUserFactory.getUser("alargo",20);
        User userToFollow = UtilUserFactory.getUser("cmorales",21);
        when(userService.findUserById(user.getId())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> followService.saveFollow(user.getId(), userToFollow.getId()));
        verify(userService).findUserById(user.getId());
    }

    /**
     * Unit Test to verify that when a user attempts to follow another regular user,
     * a BadRequestException is thrown, as regular users cannot follow each other.
     */
    @Test
    void givenUserFollowUser_whenSaveFollow_thenThrowsBadRequestException(){
        User user = UtilUserFactory.getUser("alargo", 10);
        User userToFollow = UtilUserFactory.getUser("cmorales",11);
        userToFollow.setUserType(UserType.USER);
        when(userService.findUserById(user.getId())).thenReturn(user);
        when(userService.findUserById(userToFollow.getId())).thenReturn(userToFollow);

        assertThrows(BadRequestException.class, () -> followService.saveFollow(user.getId(), userToFollow.getId()));
        verify(userService, atLeastOnce()).findUserById(user.getId());
        verify(userService, atLeastOnce()).findUserById(userToFollow.getId());
    }

    /**
     * Unit Test to verify that when an existing follow relationship is found,
     * a BadRequestException is thrown, indicating that the user cannot follow the same person twice.
     */
    @Test
    void givenExistsFollow_whenSaveFollow_thenThrowsBadRequestException(){
        User user = UtilUserFactory.getUser("alargo",11);
        User userToFollow = UtilUserFactory.getUser("cmorales",12);
        userToFollow.setUserType(UserType.SELLER);
        Follow follow = UtilFollowFactory.getFollow(user, userToFollow);
        when(userService.findUserById(user.getId())).thenReturn(user);
        when(userService.findUserById(userToFollow.getId())).thenReturn(userToFollow);
        when(followRepository.findFollow(user, userToFollow)).thenReturn(Optional.of(follow));

        assertThrows(BadRequestException.class, () -> followService.saveFollow(user.getId(), userToFollow.getId()));
        verify(userService, atLeastOnce()).findUserById(user.getId());
        verify(userService, atLeastOnce()).findUserById(userToFollow.getId());
        verify(followRepository, atLeastOnce()).findFollow(user, userToFollow);
    }

    /**
     * Unit Test to verify that when a user attempts to follow themselves,
     * a BadRequestException is thrown, as users cannot follow themselves.
     */
    @Test
    void givenUserFollowThemselves_whenSaveFollow_thenThrowsBadRequestException(){
        User user = UtilUserFactory.getUser("alargo");
        User userToFollow = UtilUserFactory.getUser("cmorales");
        when(userService.findUserById(user.getId())).thenReturn(user);
        when(userService.findUserById(userToFollow.getId())).thenReturn(userToFollow);

        assertThrows(BadRequestException.class, () -> followService.saveFollow(user.getId(), userToFollow.getId()));
        verify(userService, atLeastOnce()).findUserById(user.getId());
        verify(userService, atLeastOnce()).findUserById(userToFollow.getId());
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