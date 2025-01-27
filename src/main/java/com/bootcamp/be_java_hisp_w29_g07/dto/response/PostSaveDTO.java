package com.bootcamp.be_java_hisp_w29_g07.dto.response;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Post save dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveDTO {
    private String message;
    private PostDTO post;
}
