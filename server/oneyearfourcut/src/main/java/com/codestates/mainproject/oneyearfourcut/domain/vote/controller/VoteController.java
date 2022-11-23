package com.codestates.mainproject.oneyearfourcut.domain.vote.controller;

import com.codestates.mainproject.oneyearfourcut.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    // 좋아요 등록(생성)과 좋아요 취소(수정)가 동시에 이루어지기 때문에 Post -> Put으로 변경했습니다.
    @PutMapping("/galleries/{gallery-id}/artworks/{artwork-id}/vote")
    public ResponseEntity postVote(@PathVariable("gallery-id") Long galleryId,
                                   @PathVariable("artwork-id") Long artworkId) {

        //jwt로 로그인 회원id를 가져온다 -> jwt를 이용한 로그인 정보 가져오기 로직은 Service에서 처리하겠습니다.(원강)

        //memberId와 artworkId로 해당 좋아요가 존재하는지 체크, 있으면 삭제, 없으면 등록
        voteService.updateVote(galleryId, artworkId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
