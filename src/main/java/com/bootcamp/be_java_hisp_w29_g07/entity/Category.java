package com.bootcamp.be_java_hisp_w29_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Category.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /**
     * The Id.
     */
    public Integer id;
    /**
     * The Name.
     */
    public String name;
}
