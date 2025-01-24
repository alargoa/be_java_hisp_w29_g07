package com.bootcamp.be_java_hisp_w29_g07.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Promo count post dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoCountPostDTO {
    private Integer user_id;
    private String user_name;
    private Integer promo_products_count;
}
