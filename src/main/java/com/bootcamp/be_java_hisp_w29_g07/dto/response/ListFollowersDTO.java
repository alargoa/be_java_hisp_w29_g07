package com.bootcamp.be_java_hisp_w29_g07.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the list of followers of a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListFollowersDTO {
    Integer user_id;
    String user_name;
    List<FollowerDTO> followers;

}
