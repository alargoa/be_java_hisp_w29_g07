package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import org.springframework.stereotype.Service;


@Service
public class FollowServiceImpl implements IFollowService{

    private final IFollowRepository followRepository;
    private final IUserRepository userRepository;

    public FollowServiceImpl(IFollowRepository followRepository, IUserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

}
