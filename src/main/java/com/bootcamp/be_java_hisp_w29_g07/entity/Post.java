package com.bootcamp.be_java_hisp_w29_g07.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The type Post.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    /**
     * The Id.
     */
    @JsonProperty("post_id")
    private Integer id;
    /**
     * The User id.
     */
    @JsonProperty("user_id")
    private Integer userId;
    /**
     * The Date.
     */
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    /**
     * The Product.
     */
    private Product product;
    /**
     * The Category.
     */
    private Integer category;
    /**
     * The Price.
     */
    private Double price;
    /**
     * The Has promo.
     */
    @JsonProperty("has_promo")
    private Boolean hasPromo = false;
    /**
     * The Discount.
     */
    private Double discount = 0.0;
}
