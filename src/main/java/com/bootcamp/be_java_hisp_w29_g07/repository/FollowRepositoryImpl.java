package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Follow repository implementation.
 * This class implements the IFollowRepository interface
 */
@Repository
public class FollowRepositoryImpl implements IFollowRepository {
    /**
     * The Follow list.
     */
    private List<Follow> followList = new ArrayList<>();

    /**
     * Save follow follow.
     * This method creates a new Follow instance representing a user following another user.
     *
     * @param user         the user who is following
     * @param userToFollow the user to be followed
     * @return the created Follow instance
     */
    @Override
    public Follow saveFollow(User user, User userToFollow) {
        Follow follow = new Follow((this.followList.size() + 1), user, userToFollow, LocalDate.now());
        followList.add(follow);
        return follow;
    }

    /**
     * Find all list.
     *
     * This method retrieves all the follow relationships stored in the repository.
     *
     * @return a list of all Follow instances
     */
    @Override
    public List<Follow> findAll() {
        return followList;
    }

    /**
     * Find follow optional.
     *
     * This method searches for a specific follow relationship between two users.
     *
     * @param user         the user who may have followed another user
     * @param userToFollow the user who may be followed
     * @return an Optional containing the Follow instance if exists, otherwise empty
     */
    @Override
    public Optional<Follow> findFollow(User user, User userToFollow) {
        return findAll()
                .stream()
                .filter(follow -> follow.getFollower().getId().equals(user.getId()))
                .filter(follow -> follow.getFollowed().getId().equals(userToFollow.getId()))
                .findFirst();
    }

    /**
     * Count by followed id long.
     *
     *This method counts how many users are being followed by a specific user
     * identified by their user ID.
     * @param userId the user id of the user being followed
     * @return the count of Follow instances for the specified userId
     */
    @Override
    public Long countByFollowedId(Integer userId) {
        return this.followList.stream()
                .filter(f -> f.getFollowed().getId().equals(userId))
                .count();
    }

    /**
     * Find followed by user id list.
     *
     * This method retrieves a list of Follow instances where the specified user
     * is the follower.
     * @param userId the user id of the follower
     * @return a list of Follow instances for the specified userId
     */
    @Override
    public List<Follow> findFollowedByUserId(Integer userId) {
        return this.followList
                .stream()
                .filter(follow -> follow.getFollower().getId().equals(userId))
                .toList();
    }

    /**
     * Find followers by user id list.

     * This method retrieves a list of Follow instances where the specified user
     * is being followed.
     * @param userId the user id of the followed user
     * @return a list of Follow instances for the specified userId
     */
    @Override
    public List<Follow> findFollowersByUserId(Integer userId) {
        return this.followList
                .stream()
                .filter(follow -> follow.getFollowed().getId().equals(userId))
                .toList();
    }

    /**
     * Delete follow user by id boolean.
     * This method attempts to delete a follow relationship between a user and another user.
     * @param userId           the user id of the follower
     * @param userIdToUnfollow the user id of the user to unfollow
     * @return true if the follow relationship was deleted, otherwise false
     */
    @Override
    public Boolean deleteFollowUserById(Integer userId, Integer userIdToUnfollow) {
        Optional<Follow> optionalFollow = followList.stream()
                .filter(
                        f -> f.getFollower().getId().equals(userId)
                                && f.getFollowed().getId().equals(userIdToUnfollow))
                .findFirst();

        if (optionalFollow.isEmpty()) {
            return false;
        }

        followList.remove(optionalFollow.get());
        return true;
    }

}
