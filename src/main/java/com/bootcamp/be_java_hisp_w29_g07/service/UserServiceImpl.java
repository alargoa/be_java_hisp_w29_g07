package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;

    public UserServiceImpl (IUserRepository userRepository){

        this.userRepository = userRepository;
    }


}
