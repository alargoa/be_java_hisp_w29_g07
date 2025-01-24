package com.bootcamp.be_java_hisp_w29_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The type Follow.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Follow {
    /**
     * The Id.
     */
    private Integer id;
    /**
     * The Follower.
     */
    private User follower;
    /**
     * The Followed.
     */
    private User followed;
    /**
     * The Follow date.
     */
    private LocalDate followDate;
}
