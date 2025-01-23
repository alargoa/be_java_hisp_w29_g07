package com.bootcamp.be_java_hisp_w29_g07.service;


import com.bootcamp.be_java_hisp_w29_g07.entity.Post;

import java.util.List;

public interface IPostService {
    List<Post> listPostByUser(long userId);
}
