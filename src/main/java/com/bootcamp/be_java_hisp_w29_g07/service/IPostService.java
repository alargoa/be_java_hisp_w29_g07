package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PostSaveDTO;

import java.util.List;
import java.util.Optional;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoCountPostDTO;

public interface IPostService {
    PromoCountPostDTO findPromoPostCountByUserId(Integer userId);
    PostSaveDTO addPost(PostDTO post);
    Optional<PostDTO> findPostById(Integer id);
    List<PostDTO> findAll();
}
