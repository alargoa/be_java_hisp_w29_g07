package com.bootcamp.be_java_hisp_w29_g07.util;

import com.bootcamp.be_java_hisp_w29_g07.dto.request.ProductDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.request.PromoPostDTOIn;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTOOut;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.Objects;

private static ObjectMapper mapper;

    public static Post getPost() {
        Product product = new Product(
                1,
                "Laptop",
                "Electronics",
                "Dell",
                "Black",
                "15-inch screen, Intel i7"
        );

        return new Post(
                1,
                2,
                LocalDate.of(2024, 2, 6),
                product,
                5,
                200.0,
                false,
                0.0
        );
    }

    public static Post getPostWithId(Integer id){
        Post post = getPost();
        post.setId(id);
        return post;
    }

    public static PromoPostDTOIn getPromoPostDTOIn(Post post){
        PromoPostDTOIn promoPostDTOIn = new PromoPostDTOIn();
        promoPostDTOIn.setHas_promo(post.getHasPromo());
        promoPostDTOIn.setUser_id(post.getUserId());
        promoPostDTOIn.setCategory(post.getCategory());
        promoPostDTOIn.setDate(post.getDate());
        promoPostDTOIn.setPrice(post.getPrice());
        promoPostDTOIn.setDiscount(post.getDiscount());
        promoPostDTOIn.setProduct(getObjectMapper().convertValue(post.getProduct(), ProductDTO.class));
        return promoPostDTOIn;
    }

    public static PromoPostDTOOut getPromoPostDTOOut(Post post){
        return getObjectMapper().convertValue(post, PromoPostDTOOut.class);
    }

    public static Post getPromoPost(){
        Post promoPost = getPost();
        promoPost.setHasPromo(true);
        promoPost.setDiscount(2.0);
        return promoPost;
    }

    private static ObjectMapper getObjectMapper(){
        if(Objects.isNull(mapper)){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.findAndRegisterModules();
            return mapper;
        }

        return mapper;
    }
}