package com.codestates.mainproject.oneyearfourcut.domain.follow.repository;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.follow.entity.Follow;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> , PagingAndSortingRepository<Follow, Long> {

    Boolean existsByMember_MemberIdAndFollowMemberId(Long loginMemberId, Long targetMemberId);
    Optional<Follow> findByMemberAndGallery(Member member, Gallery gallery);

    Optional<Follow> findByFollowMemberIdAndMemberAndGallery(Long followMemberId, Member member, Gallery gallery);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE from Follow f where f.gallery = :galleryId")
    void deleteAllByGalleryId(Long galleryId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE from Follow f SET f.isFollowTogetherCheck = false where f.gallery = :galleryId")
    void updateAllFollowCheckBooleanByGalleryId(Long galleryId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE from Follow f where f.member = :memberId")
    void deleteAllByMemberId(Long memberId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE from Follow f SET f.isFollowTogetherCheck = false where f.member = :memberId")
    void updateAllFollowCheckBooleanByMemberId(Long memberId);

    List<Follow> findAllByFollowMemberIdOrderByFollowIdDesc(Long followMemberId); // 나(나의갤러리)를 팔로잉 하는 팔로워 리스트 조회

    List<Follow> findAllByMember_MemberIdOrderByFollowIdDesc(Long memberId); //내가 팔로잉하는 ...리스트 조회

}

