package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PostSaveDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements  IPostService {

    private final IPostRepository postRepository;
    // private final IUserService userRepository;
    private final ObjectMapper mapper;
    private static int idCounter = 1;


    public PostServiceImpl(IPostRepository postRepository, ObjectMapper mapper) {
        this.postRepository = postRepository;
        // this.userRepository = userRepository;
        this.mapper = new ObjectMapper();
    }


    @Override
    public PostSaveDTO addPost(PostDTO post) {

        Post post1 = mapper.convertValue(post, Post.class);
        post1.setId(postRepository.getNextId());
        postRepository.addPost(post1);

        return new PostSaveDTO("El post fue creado con exito", post1);

    }

    @Override
    public Optional<PostDTO> findPostById(Integer id) {
        Optional<Post> posId = postRepository.findPostById(id);

        return posId.map(post -> mapper.convertValue(post, PostDTO.class));
    }

    @Override
    public List<PostDTO> getAll() {
        List<Post> posts = postRepository.getAll();
        return posts.stream()
                .map(p -> mapper.convertValue(p, PostDTO.class))
                .toList();


    }
}
