package com.bootcamp.be_java_hisp_w29_g07.service;


import com.bootcamp.be_java_hisp_w29_g07.dto.response.SellerFollowerCountDTO;

public interface IFollowService {
    SellerFollowerCountDTO getSellerFollowerCount(Integer userId);
}
