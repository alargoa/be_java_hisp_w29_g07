package com.bootcamp.be_java_hisp_w29_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Follow {
    private Integer id;
    private User follower;
    private User followed;
    private LocalDate followDate;
}
