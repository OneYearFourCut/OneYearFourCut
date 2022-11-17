package com.codestates.mainproject.oneyearfourcut.domain.vote.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {
    //좋아요 누르기
    @PostMapping("/galleries/{gallery-id}/artworks/{artwork-id}/vote")
    public ResponseEntity postVote(@PathVariable("gallery-id") Long galleryId,
                                   @PathVariable("artwork-id") Long artworkId) {

        //jwt로 로그인 회원id를 가져온다
        //memberId와 artworkId로 해당 좋아요가 존재하는지 체크, 있으면 삭제, 없으면 등록

        return new ResponseEntity(HttpStatus.OK);
    }
}
