package com.codestates.mainproject.oneyearfourcut.domain.Like.repository;

import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.LikeStatus;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArtworkLikeRepository extends JpaRepository<ArtworkLike, Long> {

    Optional<ArtworkLike> findByMemberAndArtwork(Member member, Artwork artwork);

    Boolean existsByMember_MemberIdAndArtwork_ArtworkIdAndStatus(Long memberId, Long artworkId, LikeStatus status);

}
