package com.codestates.mainproject.oneyearfourcut.domain.follow.entity;

import com.codestates.mainproject.oneyearfourcut.domain.follow.dto.FollowingResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.follow.dto.FollowerResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "follow")
@NoArgsConstructor
@Entity
public class Follow extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id", nullable = false)
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //팔로우 하는 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;

    @Column(name = "follow_member_id")
    private Long followMemberId; //팔로우 당하는 id

    @Column(nullable = false)
    private Boolean isFollowTogetherCheck;

    @Builder
    public Follow(Long followId, Member member, Long followMemberId, Boolean isFollowTogetherCheck, Gallery gallery) {
        this.followId = followId;
        this.member = member;
        this.followMemberId = followMemberId;
        this.isFollowTogetherCheck = isFollowTogetherCheck;
        this.gallery = gallery;
    }

    // test용 setter
    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public void changeFollowTogetherCheck( Boolean followingTogetherCheck ){
        this.isFollowTogetherCheck = followingTogetherCheck;
    }

    public FollowingResponseDto toFollowingResponseDto(){
        return FollowingResponseDto.builder()
                .followId(this.getFollowId())
                .memberNickname(this.getGallery().getMember().getNickname())
                .galleryId(this.getGallery().getGalleryId())
                .galleryTitle(this.getGallery().getTitle())
                .profile(this.getGallery().getMember().getProfile())
                .isFollowTogetherCheck(this.getIsFollowTogetherCheck())
                .build();
    }

    public FollowerResponseDto toFollowerResponseDto(){
        return FollowerResponseDto.builder()
                .followId(this.getFollowId())
                .memberNickname(this.getGallery().getMember().getNickname())
                .galleryId(this.getGallery().getGalleryId())
                .galleryTitle(this.getGallery().getTitle())
                .profile(this.getGallery().getMember().getProfile())
                .isFollowTogetherCheck(this.getIsFollowTogetherCheck())
                .build();
    }
}
