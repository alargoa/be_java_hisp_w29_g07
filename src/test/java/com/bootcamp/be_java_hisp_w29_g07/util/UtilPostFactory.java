package com.bootcamp.be_java_hisp_w29_g07.util;

import com.bootcamp.be_java_hisp_w29_g07.dto.request.ProductDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.request.PromoPostDTOIn;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTOOut;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.Product;

import java.time.LocalDate;

/**
 * Factory class for creating {@link Post} objects with predefined values.
 * This class is used to simplify the creation of {@link Post} instances, particularly
 * in test scenarios where mock posts are needed.
 */
public class UtilPostFactory {


    /**
     * Creates a new instance of {@link Post} with predefined values.
     *
     * @return a {@link Post} object with default values.
     */
    public static Post getPost() {
        Product product = new Product(
                1,
                "Laptop",
                "Electronics",
                "Dell",
                "Black",
                "15 inch screen Intel i7"
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

    /**
     * Converts a {@link Post} object to a {@link PromoPostDTOIn} object.
     *
     * @param post the post to convert.
     * @return a {@link PromoPostDTOIn} object with values from the specified post.
     */
    public static PromoPostDTOIn getPromoPostDTOIn(Post post){
        PromoPostDTOIn promoPostDTOIn = new PromoPostDTOIn();
        promoPostDTOIn.setHas_promo(post.getHasPromo());
        promoPostDTOIn.setUser_id(post.getUserId());
        promoPostDTOIn.setCategory(post.getCategory());
        promoPostDTOIn.setDate(post.getDate());
        promoPostDTOIn.setPrice(post.getPrice());
        promoPostDTOIn.setDiscount(post.getDiscount());
        promoPostDTOIn.setProduct(UtilObjectMapper.getObjectMapper().convertValue(post.getProduct(), ProductDTO.class));
        return promoPostDTOIn;
    }

    /**
     * Converts a {@link Post} object to a {@link PromoPostDTOOut} object.
     *
     * @param post the post to convert.
     * @return a {@link PromoPostDTOOut} object with values from the specified post.
     */
    public static PromoPostDTOOut getPromoPostDTOOut(Post post){
        return UtilObjectMapper.getObjectMapper().convertValue(post, PromoPostDTOOut.class);
    }

    /**
     * Creates a new instance of {@link Post} with predefined values and promotional details.
     *
     * @return a {@link Post} object with promotional details.
     */
    public static Post getPromoPost(){
        Post promoPost = getPost();
        promoPost.setHasPromo(true);
        promoPost.setDiscount(2.0);
        return promoPost;
    }
}
