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
@RequestMapping("/galleries")
@RequiredArgsConstructor
@Validated
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{gallery-id}/follows")
    public ResponseEntity<Object> followByMember(@LoginMember Long loginMemberId,
                                                 @Valid @PathVariable("gallery-id") Long targetGalleryId) {
        return new ResponseEntity<>(
                followService.createFollow(loginMemberId, targetGalleryId).toFollowingResponseDto(), HttpStatus.CREATED);
    }

    @GetMapping("/{gallery-id}/followings")
    public ResponseEntity<Object> getFollowingList(@Valid @PathVariable("gallery-id") Long galleryId) {
        return new ResponseEntity<>(
                followService.getFollowingListByGalleryId(galleryId), HttpStatus.OK);
    }

    @GetMapping("/{gallery-id}/followers")
    public ResponseEntity<Object> getFollowerList(@Valid @PathVariable("gallery-id") Long galleryId) {
        return new ResponseEntity<>(
                followService.getFollowerListByGalleryId(galleryId), HttpStatus.OK);
    }

    @DeleteMapping("/{gallery-id}/follows")
    public ResponseEntity<Object> unfollow(@LoginMember Long loginMemberId,
                                           @Valid @PathVariable("gallery-id") Long galleryId) {
        return new ResponseEntity<>(
                followService.unfollow(loginMemberId, galleryId), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/me/followers/{follow-id}")
    public ResponseEntity<Object> deletedFollower(@LoginMember Long loginMemberId,
                                                  @Valid @PathVariable("follow-id") Long followMemberId) {
        return new ResponseEntity<>(
                followService.deleteFollower(loginMemberId, followMemberId), HttpStatus.NO_CONTENT);
    }

     /*@GetMapping("/me/followings")
    public ResponseEntity<Object> getMyFollowingList(@LoginMember Long loginMemberId) {

        return new ResponseEntity<Object>(followService.getFollowingListByMemberId(loginMemberId), HttpStatus.OK);
    }

    @GetMapping("/me/followers")
    public ResponseEntity<Object> getMyFollowerList(@LoginMember Long loginMemberId) {

        return new ResponseEntity<Object>(followService.getFollowerListByMemberId(loginMemberId), HttpStatus.OK);
    }*/
}
