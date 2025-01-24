package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.*;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListFollowersDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.MessageDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.SellerFollowerCountDTO;

import java.util.List;

public interface IFollowService {
    SellerFollowerCountDTO getSellerFollowerCount(Integer userId);
    MessageDTO unfollowUserById(Integer userId, Integer userIdToUnfollow);
    MessageDTO saveFollow(Integer userId, Integer userIdToFollow);

    ListFollowedDTO findListFollowedByUserId(Integer userId, String order);
    ListFollowersDTO findListFollowersByUserId(Integer userId, String order);
}
