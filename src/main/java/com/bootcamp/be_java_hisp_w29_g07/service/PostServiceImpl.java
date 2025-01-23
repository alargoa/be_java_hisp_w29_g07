package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final IFollowRepository followRepository;

    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followRepository = new FollowRepositoryImpl();
    }

    public List<Post> listPostByUser(long userId) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Long> userFollowing = followRepository.userFollow(userId);
        if (userFollowing.isEmpty()) { throw new NotFoundException("User don't have followings"); }

        List<Post> posts = postRepository.findPostByUser(userFollowing, userId);
        if (posts.isEmpty()) { throw new NotFoundException("Posts not found"); }

        return posts;
    }
}
