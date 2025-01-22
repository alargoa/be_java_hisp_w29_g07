package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.PromoPostDto;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.PostRepositoryImpl;
import com.bootcamp.be_java_hisp_w29_g07.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements  IPostService {

    private final PostRepositoryImpl postRepository;
    private final UserRepositoryImpl userRepository;

    public PostServiceImpl(PostRepositoryImpl postRepository, UserRepositoryImpl userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
}
