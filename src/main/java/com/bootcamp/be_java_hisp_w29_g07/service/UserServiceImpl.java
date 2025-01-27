package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements IUserService{

    /**
     * The User repository.
     */
    private final IUserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    public UserServiceImpl (IUserRepository userRepository){

        this.userRepository = userRepository;
    }

    /**
     * Find user by id user.
     *
     * @param userId the user id
     * @return the user
     */
    @Override
    public User findUserById(Integer userId) {
        Optional<User> user = userRepository.getUserById(userId);
        if (user.isEmpty()){
            throw new NotFoundException(String.format(Messages.USER_NOT_FOUND_MSG, userId));
        }
        return user.get();

    }

    @Override
    public void verifyUserExists(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format(Messages.USER_NOT_FOUND_MSG, userId));
        }
    }
}
