package com.fitin.profile.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitin.profile.dto.FollowDto;
import com.fitin.profile.service.FollowService;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{followerId}/{followedId}")
    public ResponseEntity<FollowDto> follow(@PathVariable Long followerId, @PathVariable Long followedId) {
        FollowDto followDto = followService.follow(followerId, followedId);
        return ResponseEntity.ok(followDto);
    }

    @DeleteMapping("/{followerId}/{followedId}")
    public ResponseEntity<Void> unfollow(@PathVariable Long followerId, @PathVariable Long followedId) {
        followService.unfollow(followerId, followedId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followers/{profileId}")
    public ResponseEntity<List<FollowDto>> getFollowers(@PathVariable Long profileId) {
        List<FollowDto> followers = followService.getFollowers(profileId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{profileId}")
    public ResponseEntity<List<FollowDto>> getFollowing(@PathVariable Long profileId) {
        List<FollowDto> following = followService.getFollowing(profileId);
        return ResponseEntity.ok(following);
    }
}