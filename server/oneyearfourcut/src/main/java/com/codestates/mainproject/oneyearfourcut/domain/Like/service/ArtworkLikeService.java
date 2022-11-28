package com.codestates.mainproject.oneyearfourcut.domain.Like.service;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.Like.repository.ArtworkLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtworkLikeService {

    private final ArtworkLikeRepository artworkLikeRepository;
    private final ArtworkService artworkService;
    private final MemberService memberService;

    public void updateArtworkLike(long memberId, long galleryId, long artworkId) {
        Member findMember = memberService.findMember(memberId);
        Artwork findArtwork = artworkService.findVerifiedArtwork(galleryId, artworkId);

        // 현재 로그인한 회원이 해당 작품(artworkId)에 좋아요를 했나? -> member pk와 artwork pk의 vote가 존재하는지
        Optional<ArtworkLike> likeOptional = artworkLikeRepository.findByMemberAndArtwork(findMember, findArtwork);

        likeOptional.ifPresentOrElse(
                // like가 존재한다면 좋아요를 누른 상태이다. -> 좋아요 취소 -> 좋아요 삭제
                artworkLikeRepository::delete,
                // like가 존재하지 않는다면 좋아요를 누른 적이 없다. -> 좋아요 등록 -> 좋아요 생성
                () -> {
                    ArtworkLike artworkLike = new ArtworkLike();
                    artworkLike.setMember(findMember);
                    artworkLike.setArtwork(findArtwork);
                    artworkLikeRepository.save(artworkLike);
                });
    }
}
