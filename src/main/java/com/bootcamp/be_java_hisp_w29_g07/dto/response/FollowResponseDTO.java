package com.bootcamp.be_java_hisp_w29_g07.dto.response;

import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowResponseDTO {
    private Integer user_id;
    private String user_name;
    private List<User> followed;
}
