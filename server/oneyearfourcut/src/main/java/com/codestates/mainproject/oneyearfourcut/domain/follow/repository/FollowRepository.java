package com.codestates.mainproject.oneyearfourcut.domain.follow.repository;

import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.follow.entity.Follow;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> , PagingAndSortingRepository<Follow, Long> {

    Boolean existsByMember_MemberIdAndFollowMemberId(Long loginMemberId, Long targetMemberId);
    Optional<Follow> findByMemberAndGallery(Member member, Gallery gallery);
    List<Follow> findAllByMember_MemberIdAndGallery_StatusOrderByFollowIdDesc(Long memberId, GalleryStatus galleryStatus);//팔로잉하는 갤러리 리스트 조회 (Gallery OPEN)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "SELECT f FROM Follow f " +
            " INNER JOIN Member m"+
            " ON f.followMemberId = m.memberId"+
            " WHERE f.followMemberId =:memberId" +
            " AND m.status LIKE 'A%'" +
            " ORDER BY f.followId DESC")
    List<Follow> findAllFollowerListByMemberId(Long memberId); //해당 갤러리 팔로워 리스트 조회 (유저 ACTIVE)
    Optional<Follow> findByFollowMemberIdAndMember(Long memberId, Member myMember);
}

