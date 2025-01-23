package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FollowRepositoryImpl implements IFollowRepository {
    private final List<Follow> followList = new ArrayList<>();

    @Override
    public Follow saveFollow(User user, User userToFollow) {
        Follow follow = new Follow((this.followList.size() + 1), user, userToFollow, LocalDate.now());
        followList.add(follow);
        return follow;
    }

    @Override
    public List<Follow> findAll() {
        return followList;
    }

    @Override
    public Optional<Follow> findFollow(User user, User userToFollow) {
        return findAll()
                .stream()
                .filter(follow -> follow.getFollower().getId().equals(user.getId()))
                .filter(follow -> follow.getFollowed().getId().equals(userToFollow.getId()))
                .findFirst();
    }

    @Override
    public Long countByFollowedId(Integer userId) {
        return this.followList.stream()
                .filter(f -> f.getFollowed().getId().equals(userId))
                .count();
    }

    @Override
    public List<Follow> findFollowersByUserId(Integer userId) {
        return this.followList
                .stream()
                .filter(follow -> follow.getFollower().getId().equals(userId))
                .toList();
    }

}
