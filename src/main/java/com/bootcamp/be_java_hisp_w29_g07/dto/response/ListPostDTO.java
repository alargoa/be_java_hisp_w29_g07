package com.bootcamp.be_java_hisp_w29_g07.dto.response;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type List post dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPostDTO {
    private Integer user_id;
    private List<PostDTO> posts;
}
