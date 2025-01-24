package com.bootcamp.be_java_hisp_w29_g07.dto.request;

import com.bootcamp.be_java_hisp_w29_g07.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PromoPostDTOIn{
    private Integer user_id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private Product product;
    private Integer category;
    private Double price;
    private Boolean has_promo;
    private Double discount;
}
