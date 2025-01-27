package com.bootcamp.be_java_hisp_w29_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a category of a product.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /**
     * The unique identifier of the category.
     */
    public Integer id;
    /**
     * The name of the category.
     */
    public String name;
}
