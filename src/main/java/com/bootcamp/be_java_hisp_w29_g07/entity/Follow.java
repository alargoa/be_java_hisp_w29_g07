package com.bootcamp.be_java_hisp_w29_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represents a follow relationship between two users.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Follow {
    /**
     * The unique identifier for the follow record.
     */
    private Integer id;
    /**
     * The user who follows another user.
     */
    private User follower;
    /**
     * The user who is being followed.
     */
    private User followed;
    /**
     * The date when the follow action occurred.
     */
    private LocalDate followDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Follow)) return false;
        Follow follow = (Follow) o;
        return Objects.equals(follower.getId(), follow.follower.getId()) &&
                Objects.equals(followed.getId(), follow.followed.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower.getId(), followed.getId());
    }
}
