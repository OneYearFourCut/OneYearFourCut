package com.codestates.mainproject.oneyearfourcut.domain.vote.service;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.domain.vote.entity.Vote;
import com.codestates.mainproject.oneyearfourcut.domain.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final ArtworkService artworkService;
    private final MemberService memberService;

    public void updateVote(long galleryId, long artworkId) {
        // <========================================
        long memberId = 5L;
        Member findMember = memberService.findMember(memberId);
        Artwork findArtwork = artworkService.findVerifiedArtwork(galleryId, artworkId);
        // <====== JWT 구현 시 로그인 유저 정보 가져오는 로직 대체 예정

        // 현재 로그인한 회원이 해당 작품(artworkId)에 좋아요를 했나? -> member pk와 artwork pk의 vote가 존재하는지
        Optional<Vote> voteOptional = voteRepository.findByMemberAndArtwork(findMember, findArtwork);

        voteOptional.ifPresentOrElse(
                // vote가 존재한다면 좋아요를 누른 상태이다. -> 좋아요 취소 -> 좋아요 삭제
                vote -> {
                    voteRepository.delete(vote);
                },
                // vote가 존재하지 않는다면 좋아요를 누른 적이 없다. -> 좋아요 등록 -> 좋아요 생성
                () -> {
                    Vote vote = new Vote();
                    vote.setMember(findMember);
                    vote.setArtwork(findArtwork);
                    voteRepository.save(vote);
                }
        );
    }
}
