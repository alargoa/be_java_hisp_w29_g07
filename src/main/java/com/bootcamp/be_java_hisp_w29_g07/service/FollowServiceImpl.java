package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.Enum.OrderType;
import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.FollowerDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListFollowersDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.MessageDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.SellerFollowerCountDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.*;
import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FollowServiceImpl implements IFollowService{

    private final IFollowRepository followRepository;
    private final IUserService userService;

    public FollowServiceImpl(
            IFollowRepository followRepository,
            IUserService userService
    ) {
        this.followRepository = followRepository;
        this.userService = userService;
    }

    @Override
    public SellerFollowerCountDTO getSellerFollowerCount(Integer userId) {
        User userFound = this.userService.findUserById(userId);
        if (!userFound.getUserType().equals(UserType.SELLER)) {
            throw new BadRequestException(Messages.USER_NOT_SELLER_MSG);
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
        // Validates that the user exists
        userService.findUserById(userId);
        // Validates that the user to unfollow exists
        userService.findUserById(userIdToUnfollow);

        Boolean isUnfollowed = followRepository.deleteFollowUserById(userId, userIdToUnfollow);
        if (!isUnfollowed) {
            throw new NotFoundException(String.format(Messages.USER_IS_NOT_FOLLOWING_USER, userId, userIdToUnfollow));
        }

        return new MessageDTO(String.format(Messages.USER_HAS_UNFOLLOWED_USER, userId, userIdToUnfollow));
    }

    @Override
    public MessageDTO saveFollow(Integer userId, Integer userIdToFollow) {
        // Validates that the user exists
        User user = userService.findUserById(userId);
        User userToFollow = userService.findUserById(userIdToFollow);

        if(user.getUserType().equals(UserType.SELLER)){
            throw new BadRequestException(Messages.SELLER_CANNOT_FOLLOW_SELLER);
        }
        if(userToFollow.getUserType().equals(UserType.USER)){
            throw new BadRequestException(Messages.USER_NOT_SELLER_MSG);
        }
        if(user.getId().equals(userToFollow.getId())){
            throw new BadRequestException(Messages.USER_NOT_FOLLOW_THEMSELVES_MSG);
        }
        Optional<Follow> existFollow = followRepository.findFollow(user, userToFollow);
        if(existFollow.isPresent()){
            throw new BadRequestException(Messages.USER_ALREADY_FOLLOW_SELLER);
        }
        followRepository.saveFollow(user, userToFollow);
        return new MessageDTO(String.format(Messages.USER_FOLLOW_SELLER, user.getName(), userToFollow.getName()));
    }

    @Override
    public ListFollowedDTO findListFollowedByUserId(Integer userId, String order) {
        // Validates that the user exists
        User user = userService.findUserById(userId);
        List<FollowedDTO> followList =  new ArrayList<>(followRepository.findFollowedByUserId(userId)
                .stream()
                .map(follow -> new FollowedDTO(follow.getFollowed().getId(), follow.getFollowed().getName()))
                .toList());

        followList = orderList(followList, order, Comparator.comparing(FollowedDTO::getUserName));

        return new ListFollowedDTO(user.getId(), user.getUsername(), followList);
    }

    @Override
    public ListFollowersDTO findListFollowersByUserId(Integer userId, String order) {
        // Validates that the user exists
        User user = userService.findUserById(userId);
        List<FollowerDTO> followList = new ArrayList<>(followRepository.findFollowersByUserId(userId)
                .stream()
                .map(follow -> new FollowerDTO(follow.getFollower().getId(), follow.getFollower().getName()))
                .toList());

        followList = orderList(followList, order, Comparator.comparing(FollowerDTO::getUser_name));

        return new ListFollowersDTO(user.getId(), user.getUsername(), followList);
    }

    private <T> List<T> orderList(List<T> list, String order, Comparator<T> comparator) {
        if (Objects.nonNull(order)) {
            if (order.equals(OrderType.ASC.getOrderType())) {
                list.sort(comparator);
            } else if (order.equals(OrderType.DESC.getOrderType())) {
                list.sort(comparator.reversed());
            }
        }
        return list;
    }
}
