package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.dto.request.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.request.PromoPostDTOIn;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTOOut;
import com.bootcamp.be_java_hisp_w29_g07.service.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Post controller.
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Post")
public class PostController {

    /**
     * The Post service.
     */
    private final IPostService postService;

    /**
     * Instantiates a new Post controller.
     *
     * @param postService the post service
     */
    public PostController(IPostService postService) {
        this.postService = postService;
    }

    /**
     * Gets promo post count by id.
     *
     * @param userId the user id
     * @return the promo post count by id
     */
    @Operation(summary = "Count the number of posts a user has")
    @GetMapping("promo-post/count")
    private ResponseEntity<?> getPromoPostCountById(@RequestParam("user_id") Integer userId) {
        return new ResponseEntity<>(postService.findPromoPostCountByUserId(userId), HttpStatus.OK);

    }

    /**
     * Find post by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Operation(summary = "Search for a post by id")
    @GetMapping("/findPost/{id}")
    public ResponseEntity<?> findPostById(@PathVariable Integer id) {
        return new ResponseEntity<>(postService.findPostById(id), HttpStatus.OK);

    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @Operation(summary = "Get all posts")
    @GetMapping("/findAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    /**
     * Add post response entity.
     *
     * @param posDto the pos dto
     * @return the response entity
     */
    @Operation(summary = "Add new post")
    @PostMapping("/post")
    public ResponseEntity<?> addPost(@RequestBody PostDTO posDto) {
        return new ResponseEntity<>(postService.addPost(posDto), HttpStatus.OK);
    }

    /**
     * Create promo post response entity.
     *
     * @param promoPostDTOIn the promo post dto in
     * @return the response entity
     */
    @Operation(summary = "Add new promo post")
    @PostMapping("/promo-post")
    private ResponseEntity<PromoPostDTOOut> createPromoPost(@RequestBody PromoPostDTOIn promoPostDTOIn) {
        return new ResponseEntity<>(postService.createPromoPost(promoPostDTOIn), HttpStatus.OK);
    }

    /**
     * Find list users followed posts response entity.
     *
     * @param userId the user id
     * @param order  the order
     * @return the response entity
     */
    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> findListUsersFollowedPosts(
            @PathVariable Integer userId,
            @RequestParam(value = "order", required = false) String order
    ) {
        return new ResponseEntity<>(postService.findListUsersFollowedPostsByUserId(userId, order), HttpStatus.OK);
    }
}
