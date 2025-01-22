package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FollowRepositoryImpl implements IFollowRepository {
    private List<Follow> followList = new ArrayList<>();

    public FollowRepositoryImpl() {

    }


}
