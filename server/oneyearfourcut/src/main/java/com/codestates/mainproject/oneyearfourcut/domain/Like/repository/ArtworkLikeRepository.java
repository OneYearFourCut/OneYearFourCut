package com.codestates.mainproject.oneyearfourcut.domain.Like.repository;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtworkLikeRepository extends JpaRepository<ArtworkLike, Long> {

    Optional<ArtworkLike> findByMemberAndArtwork(Member member, Artwork artwork);

    Boolean existsByMember_MemberIdAndArtwork_ArtworkId(Long memberId, Long artworkId);

}
