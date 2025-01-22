package com.bootcamp.be_java_hisp_w29_g07.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoPostDto {
    private Integer userId;
    private String userName;
    private Integer promoProductsCount;
}
