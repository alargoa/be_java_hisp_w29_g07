package com.bootcamp.be_java_hisp_w29_g07.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Product.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    /**
     * The Id.
     */
    @JsonProperty("product_id")
    private Integer id;
    /**
     * The Name.
     */
    @JsonProperty("product_name")
    private String name;
    /**
     * The Type.
     */
    private String type;
    /**
     * The Brand.
     */
    private String brand;
    /**
     * The Color.
     */
    private String color;
    /**
     * The Notes.
     */
    private String notes;
}
