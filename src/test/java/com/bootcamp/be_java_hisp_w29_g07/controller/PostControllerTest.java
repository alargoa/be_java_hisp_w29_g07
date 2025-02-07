package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.service.IPostService;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilPostFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit test class for {@link PostController}.
 */
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    /**
     * Mocked instance of {@link IPostService} used for testing the controller's behavior
     * without calling the actual service methods.
     */
    @Mock
    private IPostService postService;

    /**
     * Instance of {@link PostController} with mocked dependencies injected for unit testing.
     * The controller's methods are tested here using mocked services.
     */
    @InjectMocks
    private PostController postController;

    /**
     * Unit Test to verify that when a valid user ID is provided,
     * the controller returns a ResponseEntity containing the expected ListPostDTO with HTTP status OK.
     */
    @Test
    public void givenExistingUserId_WhenfindListUsersFollowedPosts_ThenReturnSuccessResponseEntity(){
        Integer userIdA = 1;
        Integer userIdB = 2;
        List<PostDTO> postDTOList = UtilPostFactory.getPostDTOList(userIdB);
        ListPostDTO listPostDTO = new ListPostDTO(userIdA, postDTOList);
        when(postService.findListUsersFollowedPostsByUserId(userIdA, null))
                .thenReturn(listPostDTO);

        ResponseEntity<ListPostDTO> response = postController.findListUsersFollowedPosts(userIdA, null);

        verify(postService, atLeastOnce()).findListUsersFollowedPostsByUserId(userIdA, null);
        assertNotNull(response.getBody());
        assertEquals(listPostDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Unit Test to verify that when a valid post ID is provided,
     * the controller returns a ResponseEntity containing the expected PostDTO with HTTP status OK.
     */
    @Test
    public void givenExistingPosId_WhenFindPostById_ThenReturnSuccessResponseEntity(){
        Integer postId = 1;
        PostDTO postDTO = UtilPostFactory.getPostDTO(1);
        when(postService.findPostById(postId)).thenReturn(postDTO);

        ResponseEntity<?> response = postController.findPostById(postId);

        verify(postService, atLeastOnce()).findPostById(postId);
        assertNotNull(response.getBody());
        assertEquals(postDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
