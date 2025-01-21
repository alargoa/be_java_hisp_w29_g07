package com.bootcamp.be_java_hisp_w29_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Integer id;
    private String username;
    private String name;
    private String lastname;
    private String email;
}
