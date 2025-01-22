package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository{
    private List<User> users = new ArrayList<>();

    public UserRepositoryImpl(List<User> users) {
        // TO-DO: Build json file
        this.users = users;
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        return users.stream().filter(u -> u.getId().equals(userId)).findFirst();
    }
}
