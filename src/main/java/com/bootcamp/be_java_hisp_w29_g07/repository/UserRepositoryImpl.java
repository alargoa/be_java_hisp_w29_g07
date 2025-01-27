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

/**
 * The type User repository.
 * This class implements the IUserRepository interface for managing user entities.
 */
@Repository
public class UserRepositoryImpl implements IUserRepository{
    /**
     * The list of users.
     * This list stores all the users in memory.
     */
    private List<User> users = new ArrayList<>();

    /**
     * Instantiates a new User repository.
     * This constructor initializes the repository by loading user data from a JSON file.
     * @throws IOException the io exception if there is an error loading the users
     */
    public UserRepositoryImpl() throws IOException {
        loadUsersJson();
    }

    /**
     * Gets user by id.

     *This method retrieves a user based on the provided user ID.
     *If the user is found, it returns an Optional containing the User instance;
     * @param userId the ID of the user to be retrieved
     * @return an Optional containing the User instance if found, otherwise empty
     */
    @Override
    public Optional<User> getUserById(Integer userId) {
        return users.stream().filter(u -> u.getId().equals(userId)).findFirst();
    }

    /**
     * Load users json.
     * This method loads user data from a JSON file located in the classpath.
     * @throws IOException the io exception if there is an error loading the JSON file

     */
    private void loadUsersJson() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> usersJson;

        file = ResourceUtils.getFile("classpath:users.json");
        usersJson = objectMapper.readValue(file, new TypeReference<List<User>>() {});

        users = usersJson;
    }
}
