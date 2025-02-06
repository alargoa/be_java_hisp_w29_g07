package com.bootcamp.be_java_hisp_w29_g07.util;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;

import java.time.LocalDate;

public class UtilFollowFactory {

    public static Follow getFollow(User user, User userToFollow) {
        return new Follow(1, user, userToFollow, LocalDate.now());
    }
}