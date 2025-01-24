package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.*;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListFollowersDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.MessageDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.SellerFollowerCountDTO;

/**
 * The interface Follow service.
 */
public interface IFollowService {
    /**
     * Gets seller follower count.
     *
     * @param userId the user id
     * @return the seller follower count
     */
    SellerFollowerCountDTO getSellerFollowerCount(Integer userId);

    /**
     * Unfollow user by id message dto.
     *
     * @param userId           the user id
     * @param userIdToUnfollow the user id to unfollow
     * @return the message dto
     */
    MessageDTO unfollowUserById(Integer userId, Integer userIdToUnfollow);

    /**
     * Save follow message dto.
     *
     * @param userId         the user id
     * @param userIdToFollow the user id to follow
     * @return the message dto
     */
    MessageDTO saveFollow(Integer userId, Integer userIdToFollow);

    /**
     * Find list followed by user id list followed dto.
     *
     * @param userId the user id
     * @param order  the order
     * @return the list followed dto
     */
    ListFollowedDTO findListFollowedByUserId(Integer userId, String order);

    /**
     * Find list followers by user id list followers dto.
     *
     * @param userId the user id
     * @param order  the order
     * @return the list followers dto
     */
    ListFollowersDTO findListFollowersByUserId(Integer userId, String order);
}
