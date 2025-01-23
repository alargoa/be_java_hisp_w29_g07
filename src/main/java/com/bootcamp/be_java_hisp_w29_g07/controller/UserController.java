package com.bootcamp.be_java_hisp_w29_g07.controller;

import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListFollowersDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.MessageDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.SellerFollowerCountDTO;
import com.bootcamp.be_java_hisp_w29_g07.service.IFollowService;
import com.bootcamp.be_java_hisp_w29_g07.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final IFollowService followService;

    public UserController(IUserService userService, IFollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<SellerFollowerCountDTO> getSellerFollowerCount(@PathVariable Integer userId) {
        return new ResponseEntity<>(this.followService.getSellerFollowerCount(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<MessageDTO> addFollow(@PathVariable Integer userId, @PathVariable Integer userIdToFollow) {
        return new ResponseEntity<>(followService.saveFollow(userId, userIdToFollow), HttpStatus.OK);
    }

    @GetMapping("{userId}/followers/list")
    public ResponseEntity<ListFollowersDTO> listFollowers(@PathVariable Integer userId, @RequestParam(value = "order", required = false) String order){
        return new ResponseEntity<>(followService.listFollowers(userId, order), HttpStatus.OK);
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<MessageDTO> unfollowUserById(@PathVariable Integer userId, @PathVariable Integer userIdToUnfollow) {
        return new ResponseEntity<>(followService.unfollowUserById(userId, userIdToUnfollow), HttpStatus.OK);
    }
}
