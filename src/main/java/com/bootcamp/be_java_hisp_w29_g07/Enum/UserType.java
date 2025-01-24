package com.bootcamp.be_java_hisp_w29_g07.Enum;

/**
 * The enum User type.
 */
public enum UserType {

    USER(1, "User"),
    SELLER(2, "Seller");

    private final Integer id;
    private final String userType;

    private UserType(Integer id, String userType) {
        this.id = id;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public String getUserType() {
        return userType;
    }
}
