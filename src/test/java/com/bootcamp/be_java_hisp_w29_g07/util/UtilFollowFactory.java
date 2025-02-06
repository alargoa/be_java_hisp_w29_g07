package com.bootcamp.be_java_hisp_w29_g07.util;

import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;

import java.time.LocalDate;

/**
 * Factory class for creating {@link Follow} objects with predefined values.
 * This class is used to simplify the creation of {@link Follow} instances, particularly
 * in test scenarios where mock follow relationships are needed.
 */
public class UtilFollowFactory {

    /**
     * Creates a new instance of {@link Follow} with a predefined set of values.
     * This method establishes a "follow" relationship between two {@link User} objects.
     *
     * @param user the {@link User} who is following another user.
     * @param userToFollow the {@link User} who is being followed.
     * @return a {@link Follow} object representing the follow relationship between the two users.
     */
    public static Follow getFollow(User user, User userToFollow) {
        return new Follow(1, user, userToFollow, LocalDate.now());
    }
}
