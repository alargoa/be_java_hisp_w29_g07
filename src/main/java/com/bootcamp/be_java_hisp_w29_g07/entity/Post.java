package com.bootcamp.be_java_hisp_w29_g07.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    private Integer id;
    private Integer userId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    private Product product;
    private Category category;
    private Double price;
    private Boolean hasPromo;
    private Double discount;

}
