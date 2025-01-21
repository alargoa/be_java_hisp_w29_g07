package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.repository.PostRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements  IPostService {

    private final PostRepositoryImpl repository;

    public  PostServiceImpl(PostRepositoryImpl repository)
    {
        this.repository = repository;
    }
}
