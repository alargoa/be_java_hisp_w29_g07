package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.service.IPostService;
import com.bootcamp.be_java_hisp_w29_g07.service.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        @GetMapping("/findPost/{id}")
        public ResponseEntity<?> findPostById (@PathVariable Integer id)
        {
            return new ResponseEntity<>(postService.findPostById(id), HttpStatus.OK);

        }

        @GetMapping("/findAll")
        public ResponseEntity<?> getAll ()
        {

            return new ResponseEntity<>(postService.getAll(), HttpStatus.OK);
        }


        @PostMapping("/post")
        public ResponseEntity<?> addPost (@RequestBody PostDTO posDto)
        {
            return new ResponseEntity<>(postService.addPost(posDto), HttpStatus.OK);
        }
    }
