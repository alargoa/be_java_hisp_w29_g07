package com.bootcamp.be_java_hisp_w29_g07.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Followed dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowedDTO {
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("user_name")
    private  String userName;


}
