package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.MessageDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.service.IFollowService;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilUserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test class for {@link UserController}.
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    /**
     * Mocked instance of {@link IFollowService} used for testing the controller's behavior
     * without calling the actual service methods.
     */
    @Mock
    private IFollowService followService;

    /**
     * Instance of {@link UserController} with mocked dependencies injected for unit testing.
     * The controller's methods are tested here using mocked services.
     */
    @InjectMocks
    private UserController userController;

    /**
     * Unit Test to verify that when a user successfully unfollows another user,
     * the appropriate success ResponseEntity with the message is returned.
     */
    @Test
    public void givenExistingFollow_whenUnfollowUserById_thenReturnSuccessResponseEntity(){
        Integer userIdFollower = 1;
        Integer userIdFollowed = 2;
        MessageDTO messageDTO = new MessageDTO(String.format(Messages.USER_HAS_UNFOLLOWED_USER, userIdFollower, userIdFollowed));
        when(followService.unfollowUserById(userIdFollower, userIdFollowed)).thenReturn(messageDTO);

        ResponseEntity<MessageDTO> response = userController.unfollowUserById(userIdFollower, userIdFollowed);

        verify(followService).unfollowUserById(userIdFollower, userIdFollowed);
        assertEquals(messageDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(messageDTO.getMessage(), response.getBody().getMessage());
    }

    /**
     * Unit Test to verify that when a user successfully follows a seller,
     * the appropriate success ResponseEntity with the message is returned.
     */
    @Test
    public void givenUserFollowSeller_whenAddFollow_thenReturnSuccessResponseEntity(){
        User user = UtilUserFactory.getUser("jfeo");
        User userToFollow = UtilUserFactory.getUser("alargo");
        MessageDTO message = new MessageDTO(String.format(Messages.USER_FOLLOW_SELLER, user.getName(), userToFollow.getName()));
        when(followService.saveFollow(user.getId(), userToFollow.getId())).thenReturn(message);

        ResponseEntity<MessageDTO> response = userController.addFollow(user.getId(), userToFollow.getId());

        assertEquals(message, response.getBody());
        }
}
