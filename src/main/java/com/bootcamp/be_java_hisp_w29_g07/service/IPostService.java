package com.bootcamp.be_java_hisp_w29_g07.service;


import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTO;

public interface IPostService {
    PromoPostDTO getPromoPostCount(Integer userId);
}
