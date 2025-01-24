package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;

import java.util.List;
import java.util.Optional;

/**
 * The interface Post repository.
 */
public interface IPostRepository {
    /**
     * Find promo post count by user id long.
     *
     * @param userId the user id
     * @return the long
     */
    Long findPromoPostCountByUserId(Integer userId);

    /**
     * Save post post.
     *
     * @param post the post
     * @return the post
     */
    Post savePost(Post post);

    /**
     * Find post by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Post> findPostById(Integer id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Post> findAll();

    /**
     * Find next id integer.
     *
     * @return the integer
     */
    Integer findNextId();

    /**
     * Find posts by user ids and last two weeks list.
     *
     * @param userFollowing the user following
     * @return the list
     */
    List<Post> findPostsByUserIdsAndLastTwoWeeks(List<Integer> userFollowing);
}
