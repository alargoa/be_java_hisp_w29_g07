package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.Enum.OrderType;
import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import com.bootcamp.be_java_hisp_w29_g07.constants.ErrorMessages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.FollowerDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListFollowersDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.MessageDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.SellerFollowerCountDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FollowServiceImpl implements IFollowService {

    private final IFollowRepository followRepository;
    private final IUserRepository userRepository;
    private final IUserService userService;

    public FollowServiceImpl(
            IFollowRepository followRepository,
            IUserRepository userRepository,
            IUserService userService
    ) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public SellerFollowerCountDTO getSellerFollowerCount(Integer userId) {

        User userFound = this.userService.findUserById(userId);
        if (!userFound.getUserType().equals(UserType.SELLER)) {
            throw new BadRequestException(ErrorMessages.USER_NOT_SELLER_MSG);
        }

        Long followerCount = this.followRepository.countByFollowedId(userId);
        return new SellerFollowerCountDTO(
                userFound.getId(),
                userFound.getUsername(),
                followerCount
        );
    }

    @Override
    public MessageDTO unfollowUserById(Integer userId, Integer userIdToUnfollow) {
        Boolean isUnfollowed = followRepository.deleteFollowUserById(userId, userIdToUnfollow);
        if (!isUnfollowed) {
            throw new NotFoundException(String.format(ErrorMessages.USER_IS_NOT_FOLLOWING_USER, userId, userIdToUnfollow));
        }

        return new MessageDTO(String.format(ErrorMessages.USER_HAS_UNFOLLOWED_USER, userId, userIdToUnfollow));
    }

    @Override
    public MessageDTO saveFollow(Integer userId, Integer userIdToFollow) {

        User user = userService.findUserById(userId);
        User userToFollow = userService.findUserById(userIdToFollow);

        if (userToFollow.getUserType().equals(UserType.USER)) {
            throw new BadRequestException(ErrorMessages.USER_NOT_SELLER_MSG);
        }
        if (user.getId().equals(userToFollow.getId())) {
            throw new BadRequestException(ErrorMessages.USER_NOT_FOLLOW_THEMSELVES_MSG);
        }
        Optional<Follow> existFollow = followRepository.findFollow(user, userToFollow);
        if (existFollow.isPresent()) {
            throw new BadRequestException(ErrorMessages.USER_ALREADY_FOLLOW_SELLER);
        }
        followRepository.saveFollow(user, userToFollow);
        return new MessageDTO(String.format("User %s follows user %s", user.getName(), userToFollow.getName()));
    }

    @Override
    public ListFollowersDTO listFollowers(Integer userId, String order) {
        User user = userService.findUserById(userId);
        List<FollowerDTO> followList = new ArrayList<>(followRepository.findFollowersByUserId(userId)
                .stream()
                .map(follow -> new FollowerDTO(follow.getFollower().getId(), follow.getFollower().getName()))
                .toList());

        if (order != null) {
            if (order.equals(OrderType.ASC.getOrderType())) {
                followList.sort(Comparator.comparing(FollowerDTO::getUser_name));
            } else {
                followList.sort(Comparator.comparing(FollowerDTO::getUser_name).reversed());
            }
        }
        return new ListFollowersDTO(user.getId(), user.getUsername(), followList);
    }


}
