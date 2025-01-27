package com.bootcamp.be_java_hisp_w29_g07.dto.request;

import com.bootcamp.be_java_hisp_w29_g07.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a Post from a seller
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Integer post_id;
    private Integer user_id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private Product product;
    private Integer category;
    private Double price;
    private Boolean has_promo = false;
    private Double discount = 0.0;
}
