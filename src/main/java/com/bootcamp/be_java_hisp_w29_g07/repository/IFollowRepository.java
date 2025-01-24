package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface Follow repository.
 */
public interface IFollowRepository {

    /**
     * Save follow follow.
     *
     * @param user         the user
     * @param userToFollow the user to follow
     * @return the follow
     */
    Follow saveFollow(User user, User userToFollow);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Follow> findAll();

    /**
     * Find follow optional.
     *
     * @param user         the user
     * @param userToFollow the user to follow
     * @return the optional
     */
    Optional<Follow> findFollow(User user, User userToFollow);

    /**
     * Count by followed id long.
     *
     * @param userId the user id
     * @return the long
     */
    Long countByFollowedId(Integer userId);

    /**
     * Find followed by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Follow> findFollowedByUserId(Integer userId);

    /**
     * Delete follow user by id boolean.
     *
     * @param userId           the user id
     * @param userIdToUnfollow the user id to unfollow
     * @return the boolean
     */
    Boolean deleteFollowUserById(Integer userId, Integer userIdToUnfollow);

    /**
     * Find followers by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Follow> findFollowersByUserId(Integer userId);
}
