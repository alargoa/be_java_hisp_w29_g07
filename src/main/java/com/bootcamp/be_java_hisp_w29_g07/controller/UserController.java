package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListFollowedDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListFollowersDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.MessageDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.SellerFollowerCountDTO;
import com.bootcamp.be_java_hisp_w29_g07.service.IFollowService;
import com.bootcamp.be_java_hisp_w29_g07.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/users")
@Tag(name="User")
public class UserController {

    /**
     * The User service.
     */
    private final IUserService userService;
    /**
     * The Follow service.
     */
    private final IFollowService followService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService   the user service
     * @param followService the follow service
     */
    public UserController(IUserService userService, IFollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }

    /**
     * Gets seller follower count.
     *
     * @param userId the user id
     * @return the seller follower count
     */
    @Operation(summary = "Count the number of followers a user has")
    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<SellerFollowerCountDTO> getSellerFollowerCount(@PathVariable Integer userId) {
        return new ResponseEntity<>(this.followService.getSellerFollowerCount(userId), HttpStatus.OK);
    }


    /**
     * Add follow response entity.
     *
     * @param userId         the user id
     * @param userIdToFollow the user id to follow
     * @return the response entity
     */
    @Operation(summary = "Add a new follow by user")
    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<MessageDTO> addFollow(@PathVariable Integer userId, @PathVariable Integer userIdToFollow) {
        return new ResponseEntity<>(followService.saveFollow(userId, userIdToFollow), HttpStatus.OK);
    }

    /**
     * Find list followers by user id response entity.
     *
     * @param userId the user id
     * @param order  the order
     * @return the response entity
     */
    @Operation(summary = "List of a users followers by id")
    @GetMapping("{userId}/followers/list")
    public ResponseEntity<ListFollowersDTO> findListFollowersByUserId(@PathVariable Integer userId, @RequestParam(value = "order", required = false) String order){
        return new ResponseEntity<>(followService.findListFollowersByUserId(userId, order), HttpStatus.OK);
    }

    /**
     * Find list followed by user id response entity.
     *
     * @param userId the user id
     * @param order  the order
     * @return the response entity
     */
    @Operation(summary = "List of a users followers by id")
    @GetMapping("{userId}/followed/list")
    public ResponseEntity<ListFollowedDTO> findListFollowedByUserId(@PathVariable Integer userId, @RequestParam(value = "order", required = false) String order){
        return new ResponseEntity<>(followService.findListFollowedByUserId(userId, order), HttpStatus.OK);
    }

    /**
     * Unfollow user by id response entity.
     *
     * @param userId           the user id
     * @param userIdToUnfollow the user id to unfollow
     * @return the response entity
     */
    @Operation( summary = "Unfollow a user")
    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<MessageDTO> unfollowUserById(@PathVariable Integer userId, @PathVariable Integer userIdToUnfollow) {
        return new ResponseEntity<>(followService.unfollowUserById(userId, userIdToUnfollow), HttpStatus.OK);
    }
}
