package com.bootcamp.be_java_hisp_w29_g07.service;


import com.bootcamp.be_java_hisp_w29_g07.entity.Post;

import java.util.List;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTO;

public interface IPostService {
    List<Post> listPostByUser(long userId);
    PromoPostDTO getPromoPostCount(Integer userId);
}
