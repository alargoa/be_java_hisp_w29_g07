package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.request.PromoPostDTOIn;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PostSaveDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoCountPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTOOut;

import java.util.List;

/**
 * The interface Post service.
 */
public interface IPostService {
    /**
     * Find list users followed posts by user id list post dto.
     *
     * @param userId the user id
     * @param order  the order
     * @return the list post dto
     */
    ListPostDTO findListUsersFollowedPostsByUserId(Integer userId, String order);

    /**
     * Find promo post count by user id promo count post dto.
     *
     * @param userId the user id
     * @return the promo count post dto
     */
    PromoCountPostDTO findPromoPostCountByUserId(Integer userId);

    /**
     * Add post post save dto.
     *
     * @param post the post
     * @return the post save dto
     */
    PostSaveDTO addPost(PostDTO post);

    /**
     * Find post by id optional.
     *
     * @param id the id
     * @return the post dto
     */
    PostDTO findPostById(Integer id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<PostDTO> findAll();

    /**
     * Create promo post promo post dto out.
     *
     * @param promoPostDTOIn the promo post dto in
     * @return the promo post dto out
     */
    PromoPostDTOOut createPromoPost(PromoPostDTOIn promoPostDTOIn);
}
