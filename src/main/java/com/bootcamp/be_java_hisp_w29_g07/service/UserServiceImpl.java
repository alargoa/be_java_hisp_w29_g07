package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import com.bootcamp.be_java_hisp_w29_g07.constants.ErrorMessages;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;

    public UserServiceImpl (IUserRepository userRepository){

        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(Integer userId) {
        Optional<User> user = userRepository.getUserById(userId);
        if (user.isEmpty()){
            throw new NotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_MSG, userId));
        }
        return user.get();

    }
}
