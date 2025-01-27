package com.bootcamp.be_java_hisp_w29_g07.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the list of followed sellers
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListFollowedDTO {
    private Integer id;
    private String userName;

    private List<FollowedDTO> followed;
}
