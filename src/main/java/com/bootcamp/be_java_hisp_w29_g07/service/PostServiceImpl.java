package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.PostRepositoryImpl;
import com.bootcamp.be_java_hisp_w29_g07.repository.UserRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> listPostByUser(long userId) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> posts = postRepository.findPostByUser(userId);
        if (posts.isEmpty()) {
            //throw new NotFoundException("Post not found");
        }
        return posts;
    }
}
