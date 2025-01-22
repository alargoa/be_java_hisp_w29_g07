package com.bootcamp.be_java_hisp_w29_g07.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SellerFollowerCountDTO {
    private Integer user_id;
    private String user_name;
    private Long followers_count;
}
