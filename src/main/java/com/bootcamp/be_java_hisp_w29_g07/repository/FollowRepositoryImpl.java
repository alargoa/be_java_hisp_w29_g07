package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FollowRepositoryImpl implements IFollowRepository {
    private List<Follow> followList = new ArrayList<>();

    public FollowRepositoryImpl() {

    }

    @Override
    public List<Long> userFollow(Long userId) {
        return followList.stream()
                .filter(follow -> follow.getFollower().getId().equals(userId))
                .map(follow -> follow.getFollowed().getId().longValue())
                .collect(Collectors.toList());
    }
}
