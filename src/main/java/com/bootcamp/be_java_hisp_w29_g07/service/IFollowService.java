package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.FollowResponseDTO;

import java.util.List;

public interface IFollowService {

    List<FollowResponseDTO> iFollow(Integer id);
}
