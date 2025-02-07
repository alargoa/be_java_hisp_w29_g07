package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.request.PromoPostDTOIn;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTOOut;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.service.IPostService;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilPostFactory;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilUserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit test class for {@link PostController}.
 */
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    /**
     * Mocked instance of {@link IPostService} to simulate post service operations.
     */
    @Mock
    private IPostService postService;

    /**
     * Injected instance of {@link PostController} with mocked dependencies.
     */
    @InjectMocks
    private PostController postController;

    /**
     * Unit Test to verify that when a non-existent promotional post is created,
     * a success response entity is returned.
     */
    @Test
    public void givenNonExistentPromoPost_whenCreatePromoPost_thenReturnSuccessResponseEntity() {
        User user = UtilUserFactory.getSeller("cmorales", 2);

        Post newPost = UtilPostFactory.getPromoPost();
        newPost.setId(null);

        Post savedPost = UtilPostFactory.getPromoPost();
        savedPost.setUserId(user.getId());
        PromoPostDTOOut promoPostDTOOutExpected = UtilPostFactory.getPromoPostDTOOut(savedPost);

        PromoPostDTOIn promoPostDTOIn = UtilPostFactory.getPromoPostDTOIn(newPost);
        when(postService.createPromoPost(promoPostDTOIn)).thenReturn(promoPostDTOOutExpected);

        ResponseEntity<PromoPostDTOOut> responseEntity = postController.createPromoPost(promoPostDTOIn);

        verify(postService).createPromoPost(promoPostDTOIn);
        assertEquals(promoPostDTOOutExpected, responseEntity.getBody());
    }

    /**
     * Unit Test to verify that when a valid user ID is provided,
     * the controller returns a ResponseEntity containing the expected ListPostDTO with HTTP status OK.
     */
    @Test
    public void givenExistingUserId_whenFindListUsersFollowedPosts_ThenReturnSuccessResponseEntity() {
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
     * Unit Test to verify that when a valid seller ID is provided,
     * the controller returns a ResponseEntity containing the expected ListPostDTO with HTTP status OK.
     */
    @Test
    public void givenExistingPosts_whenFindAllPostsBySellerId_thenReturnSuccessResponseEntity(){
        Integer userIdSeller = 2;
        List<PostDTO> postDTOList = UtilPostFactory.getPostDTOList(userIdSeller);
        ListPostDTO listPostDTO = new ListPostDTO(userIdSeller, postDTOList);

        when(postService.findAllPostBySellerId(userIdSeller)).thenReturn(listPostDTO);

        ResponseEntity<ListPostDTO> responseEntity = postController.findAllPostsBySellerId(userIdSeller);

        verify(postService).findAllPostBySellerId(userIdSeller);
        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody().getPosts()).containsExactlyInAnyOrderElementsOf(postDTOList);
        assertEquals(userIdSeller, responseEntity.getBody().getUser_id());
    }
}
