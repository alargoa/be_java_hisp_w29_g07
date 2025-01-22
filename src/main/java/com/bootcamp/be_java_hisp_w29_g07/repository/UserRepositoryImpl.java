package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository{
    private List<User> users = new ArrayList<>();

    public UserRepositoryImpl() throws IOException {
        loadUsersJson();
        users.forEach(System.out::println);
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        return users.stream().filter(u -> u.getId().equals(userId)).findFirst();
    }

    private void loadUsersJson() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> usersJson;

        file = ResourceUtils.getFile("classpath:users.json");
        usersJson = objectMapper.readValue(file, new TypeReference<List<User>>() {});

        users = usersJson;
    }
}
