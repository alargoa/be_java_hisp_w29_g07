package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.service.IPostService;
import com.bootcamp.be_java_hisp_w29_g07.util.UtilPostFactory;
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
import static org.mockito.Mockito.when;

/**
 * Unit test class for {@link PostController}.
 */
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
    @Mock
    private IPostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    public void givenExistingUserId_WhenfindListUsersFollowedPosts_ThenReturnSuccessResponseEntity(){
        Integer userIdA = 1;
        Integer userIdB = 2;
        List<PostDTO> postDTOList = UtilPostFactory.getPostDTOList(userIdB);
        ListPostDTO listPostDTO = new ListPostDTO(userIdA, postDTOList);
        when(postService.findListUsersFollowedPostsByUserId(userIdA, null))
                .thenReturn(listPostDTO);

        ResponseEntity<ListPostDTO> response = postController.findListUsersFollowedPosts(userIdA, null);

        assertNotNull(response.getBody());
        assertEquals(listPostDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
