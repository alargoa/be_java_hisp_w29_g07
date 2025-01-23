package com.bootcamp.be_java_hisp_w29_g07.service;


import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;

import java.util.List;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTO;

public interface IPostService {
    ListPostDTO listPostByUser(Integer userId);
    PromoPostDTO getPromoPostCount(Integer userId);
}
