package com.bootcamp.be_java_hisp_w29_g07.entity;

import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    /**
     * The Id.
     */
    private Integer id;
    /**
     * The Username.
     */
    private String username;
    /**
     * The Name.
     */
    private String name;
    /**
     * The Lastname.
     */
    private String lastname;
    /**
     * The Email.
     */
    private String email;
    /**
     * The User type.
     */
    private UserType userType;
}
