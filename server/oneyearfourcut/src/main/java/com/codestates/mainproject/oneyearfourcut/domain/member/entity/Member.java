package com.codestates.mainproject.oneyearfourcut.domain.member.entity;


import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.Alarm;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberResponseDto;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter // setter 사용중인게 있어서 등록. 나중에 제거 예정
@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String nickname;

    private String email;
    
    private String profile;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public Member(String nickname, String email, String profile, Role role, MemberStatus status) {
        this.nickname = nickname;
        this.email = email;
        this.profile = profile;
        this.role = role;
        this.status = status;
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

    public MemberResponseDto toMemberResponseDto() {
        return MemberResponseDto.builder()
                .email(this.email)
                .nickname(this.nickname)
                .profile(this.profile)
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

}
