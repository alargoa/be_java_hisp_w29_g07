package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import com.bootcamp.be_java_hisp_w29_g07.constants.ErrorMessages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.SellerFollowerCountDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class FollowServiceImpl implements IFollowService{

    private final IFollowRepository followRepository;
    private final IUserRepository userRepository;

    public FollowServiceImpl(IFollowRepository followRepository, IUserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SellerFollowerCountDTO getSellerFollowerCount(Integer userId) {
        Optional<User> userFound = this.userRepository.getUserById(userId);
        if (userFound.isEmpty()) {
            throw new NotFoundException(ErrorMessages.USER_NOT_FOUND_MSG);
        }
        if (!userFound.get().getUserType().equals(UserType.SELLER)) {
            throw new BadRequestException(ErrorMessages.USER_NOT_SELLER_MSG);
        }

        Long followerCount = this.followRepository.countByFollowedId(userId);
        return new SellerFollowerCountDTO(
                userFound.get().getId(),
                userFound.get().getUsername(),
                followerCount
        );
    }
}
