package com.codestates.mainproject.oneyearfourcut.domain.member.entity;


import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.Alarm;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberResponseDto;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import com.codestates.mainproject.oneyearfourcut.domain.refreshToken.entity.RefreshToken;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 8, nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String profile;

    private Long kakaoId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public Member(String nickname, String email, String profile, Role role, MemberStatus status, Long kakaoId) {
        this.nickname = nickname;
        this.email = email;
        this.profile = profile;
        this.role = role;
        this.status = status;
        this.kakaoId = kakaoId;
    }

    //jpa 연관관계 맵핑 위해 생성하는 member 엔티티 용 생성자
    public Member(Long memberId) {
        this.memberId = memberId;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
    public void updateProfile(String profile) {
        this.profile = profile;
    }

    public void updateStatus(MemberStatus status) {
        this.status = status;
    }
    public void updateKakaoId(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    public MemberResponseDto toMemberResponseDto() {
        List<Gallery> galleries = this.getGalleryList();
        Long galleryId = null;
        for (Gallery gallery : galleries) {
            if (gallery.getStatus() == GalleryStatus.OPEN) {
                galleryId = gallery.getGalleryId();
            }
        }
        return MemberResponseDto.builder()
                .nickname(this.nickname)
                .profile(this.profile)
                .galleryId(galleryId)
                .build();
    }

    //test 코드 용 생성자
    public Member(List<Gallery> galleryList) {
        this.galleryList = galleryList;
    }

    @OneToMany(mappedBy = "member")
    private List<Gallery> galleryList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ArtworkLike> artworkLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Artwork> artworkList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Alarm> alarmList = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private RefreshToken refreshToken;
}
