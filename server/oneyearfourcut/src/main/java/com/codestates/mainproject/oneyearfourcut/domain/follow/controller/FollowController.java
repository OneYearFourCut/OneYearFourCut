package com.codestates.mainproject.oneyearfourcut.domain.follow.controller;

import com.codestates.mainproject.oneyearfourcut.domain.follow.entity.Follow;
import com.codestates.mainproject.oneyearfourcut.domain.follow.service.FollowService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
@Validated
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{gallery-id}")
    public ResponseEntity<Object> followByMember(@LoginMember Long loginMemberId,
                                                 @Valid @PathVariable("gallery-id") Long targetGalleryId) {
        Follow follow = followService.createFollow(loginMemberId, targetGalleryId);
        return new ResponseEntity<Object>(follow.toFollowingResponseDto(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{gallery-id}")
    public ResponseEntity<Object> unfollow(@LoginMember Long loginMemberId,
                                           @Valid @PathVariable("gallery-id") Long galleryId) {
        return new ResponseEntity<Object>(followService.unfollow(loginMemberId, galleryId), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/me/followings")
    public ResponseEntity<Object> getFollowingList(@LoginMember Long loginMemberId) {

        return new ResponseEntity<Object>(followService.getFollowingListByMemberId(loginMemberId), HttpStatus.OK);
    }

    @GetMapping("/me/followers")
    public ResponseEntity<Object> getFollowerList(@LoginMember Long loginMemberId) {

        return new ResponseEntity<Object>(followService.getFollowerListByMemberId(loginMemberId), HttpStatus.OK);
    }

    @DeleteMapping("/me/followers/{member-id}")
    public ResponseEntity<Object> deletedFollower(@LoginMember Long loginMemberId,
                                                  @Valid @PathVariable("member-id") Long followMemberId) {
        return new ResponseEntity<Object>( followService.deleteFollower(loginMemberId, followMemberId), HttpStatus.NO_CONTENT);
    }
}
