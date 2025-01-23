package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.service.IPostService;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class PostController {

    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping("promo-post/count")
    private ResponseEntity<?> getPromoPostCount(@RequestParam("user_id") Integer userId) {
        return new ResponseEntity<>(postService.getPromoPostCount(userId), HttpStatus.OK);
    }

    @GetMapping("/products/followed/{userId}/list")
    public ResponseEntity<List<Post>> listFollowedPosts(@PathVariable long userId) {
        return new ResponseEntity<>(postService.listPostByUser(userId), HttpStatus.OK);
    }
}
