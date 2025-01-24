package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PostSaveDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTO;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    ListPostDTO findListUsersFollowedPosts(Integer userId);
    PromoPostDTO getPromoPostCount(Integer userId);
    PostSaveDTO addPost(PostDTO post);
    Optional<PostDTO> findPostById(Integer id);
    List<PostDTO> findAll();
}
