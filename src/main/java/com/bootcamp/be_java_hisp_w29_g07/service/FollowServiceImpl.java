package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.repository.FollowRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements IFollowService{
    private FollowRepositoryImpl followRepository;

    public FollowServiceImpl(FollowRepositoryImpl followRepository) {
        this.followRepository = followRepository;
    }
}
