package com.bootcamp.be_java_hisp_w29_g07.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the information of a follower user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerDTO {
    Integer user_id;
    String user_name;
}
