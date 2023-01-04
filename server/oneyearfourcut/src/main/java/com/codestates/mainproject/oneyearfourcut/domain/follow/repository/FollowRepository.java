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

    Optional<Follow> findByFollowMemberIdAndMemberAndGallery(Long followMemberId, Member member, Gallery gallery);

    List<Follow> findAllByMember_MemberIdAndGallery_StatusOrderByFollowIdDesc(Long memberId, GalleryStatus galleryStatus);//팔로잉하는 갤러리 리스트 조회 (Gallery OPEN)

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "SELECT f FROM Follow f " +
            " INNER JOIN Member m"+
            " ON f.followMemberId = m.memberId"+
            " WHERE f.followMemberId =:memberId" +
            " AND m.status LIKE 'A%'" +
            " ORDER BY f.followId DESC")
    List<Follow> findAllFollowerListByMemberId(Long memberId); //해당 갤러리 팔로워 리스트 조회 (유저 ACTIVE)

    Optional<Follow> findByFollowMemberIdAndGallery(Long myMemberId, Gallery myGallery);

    //-------------------- 미사용 bulk 삭제 쿼리--------------------//

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE from Follow f where f.gallery = :galleryId")
    void deleteAllFollowByGalleryId(Long galleryId);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE from Follow f SET f.isFollowTogetherCheck = false where f.gallery = :galleryId")
    void updateAllFollowCheckBooleanByGalleryId(Long galleryId);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE from Follow f where f.member = :memberId")
    void deleteAllFollowByMemberId(Long memberId);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE from Follow f where f.followMemberId = :followMemberId")
    void deleteAllFollowByFollowMemberId(Long followMemberId);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE from Follow f SET f.isFollowTogetherCheck = false where f.member = :memberId")
    void updateAllFollowCheckBooleanByMemberId(Long memberId);

    List<Follow> findAllByGallery_GalleryIdAndGallery_StatusOrderByFollowIdDesc(Long galleryId, GalleryStatus open);

}

