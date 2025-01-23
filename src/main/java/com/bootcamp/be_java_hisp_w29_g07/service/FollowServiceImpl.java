package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.FollowResponseDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.repository.FollowRepositoryImpl;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements IFollowService {

    private final IFollowRepository followRepository;
    private final IUserRepository userRepository;

    public FollowServiceImpl(IFollowRepository followRepository, IUserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<FollowResponseDTO> iFollow(Integer id) {
        ObjectMapper mapper = new ObjectMapper();
        List<User> listFollow = followRepository.iFollow(id);
        return listFollow.stream().map(f -> mapper.convertValue(f, FollowResponseDTO.class))
                .toList();
    }
    // private IFollowService followRepository;


}
