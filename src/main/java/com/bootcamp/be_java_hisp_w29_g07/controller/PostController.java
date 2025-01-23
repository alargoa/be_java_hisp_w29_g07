package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.service.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostController {

    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }


    @GetMapping("/findPost/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Integer id)
    {

        return  new ResponseEntity<>(postService.findPostById(id),HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> getAll()
    {

        return  new ResponseEntity<>(postService.getAll(),HttpStatus.OK);
    }


    @PostMapping("/post")
    public ResponseEntity<?> addPost(@RequestBody PostDTO postDTO)
    {
        return  new ResponseEntity<>(postService.addPost(postDTO),HttpStatus.OK);
    }
}
