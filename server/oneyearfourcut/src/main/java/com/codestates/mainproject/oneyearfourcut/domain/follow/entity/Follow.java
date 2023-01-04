package com.codestates.mainproject.oneyearfourcut.domain.follow.entity;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEvent;
import com.codestates.mainproject.oneyearfourcut.domain.follow.dto.FollowingResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.follow.dto.FollowerResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

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
                .galleryId(this.getGallery().getGalleryId())
                .galleryTitle(this.getGallery().getTitle())
                .galleryMemberNickname(this.getGallery().getMember().getNickname())
                .profile(this.getGallery().getMember().getProfile())
                .isFollowTogetherCheck(this.getIsFollowTogetherCheck())
                .build();
    }
    public FollowerResponseDto toFollowerResponseDto(){
        Gallery openFollowerGallery = this.getMember().getOpenGallery().orElse(new Gallery());
        return FollowerResponseDto.builder()
                .followId(this.getFollowId())
                .galleryId(openFollowerGallery.getGalleryId())
                .galleryTitle(openFollowerGallery.getTitle())
                .galleryMemberNickname(this.getMember().getNickname())
                .profile(this.getMember().getProfile())
                .isFollowTogetherCheck(this.getIsFollowTogetherCheck())
                .build();
    }
    public AlarmEvent toAlarmEvent(Long receiverId){
        return AlarmEvent.builder()
                .receiverId(receiverId)
                .senderId(this.getMember().getMemberId())
                .alarmType(AlarmType.FOLLOW)
                .galleryId(this.getGallery().getGalleryId())
                .artworkId(null)
                .build();
    }
}
