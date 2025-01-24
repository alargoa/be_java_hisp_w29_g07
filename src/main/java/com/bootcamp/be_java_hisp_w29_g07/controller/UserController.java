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

@RestController
@RequestMapping("/users")
@Tag(name="User")
public class UserController {

    private final IUserService userService;
    private final IFollowService followService;

    public UserController(IUserService userService, IFollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }

    @Operation(summary = "Count the number of followers a user has")
    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<SellerFollowerCountDTO> getSellerFollowerCount(@PathVariable Integer userId) {
        return new ResponseEntity<>(this.followService.getSellerFollowerCount(userId), HttpStatus.OK);
    }


    @Operation(summary = "Add a new follow by user")
    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<MessageDTO> addFollow(@PathVariable Integer userId, @PathVariable Integer userIdToFollow) {
        return new ResponseEntity<>(followService.saveFollow(userId, userIdToFollow), HttpStatus.OK);
    }

    @Operation(summary = "List of a users followers by id")
    @GetMapping("{userId}/followers/list")
    public ResponseEntity<ListFollowersDTO> findListFollowersByUserId(@PathVariable Integer userId, @RequestParam(value = "order", required = false) String order){
        return new ResponseEntity<>(followService.findListFollowersByUserId(userId, order), HttpStatus.OK);
    }
    @Operation(summary = "List of a users followers by id")
    @GetMapping("{userId}/followed/list")
    public ResponseEntity<ListFollowedDTO> findListFollowedByUserId(@PathVariable Integer userId, @RequestParam(value = "order", required = false) String order){
        return new ResponseEntity<>(followService.findListFollowedByUserId(userId, order), HttpStatus.OK);
    }

    @Operation( summary = "Unfollow a user")
    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<MessageDTO> unfollowUserById(@PathVariable Integer userId, @PathVariable Integer userIdToUnfollow) {
        return new ResponseEntity<>(followService.unfollowUserById(userId, userIdToUnfollow), HttpStatus.OK);
    }
}
