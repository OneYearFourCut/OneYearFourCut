package com.codestates.mainproject.oneyearfourcut.domain.artwork.entity;


import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEvent;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.OneYearFourCutResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public class Artwork extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artworkId;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 70, nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String imagePath;

    @Enumerated(EnumType.STRING)
    private ArtworkStatus status = ArtworkStatus.REGISTRATION;

    @Transient
    private MultipartFile image;

    @Formula("(select count(*) from artwork_like v where v.artwork_id = artwork_id and v.status = 'LIKE')")
    private int likeCount;
    @Transient
    private boolean liked;

    @Formula("(select count(*) from comment c where c.artwork_id = artwork_id and c.comment_status = 'VALID')")
    private int commentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GALLERY_ID")
    private Gallery gallery;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL)
    private List<ArtworkLike> artworkLikeList = new ArrayList<>();

    public Long getMemberId() {
        return this.member.getMemberId();
    }

    public int getLikeCount() {
        return this.likeCount;
    }

    public int getCommentCount() {
        return this.commentCount;
    }

    /* ################### Setter ################### */
    public void setGallery(Gallery gallery) {

        if (this.gallery != null) {
            this.gallery.getArtworkList().remove(this);
        }
        this.gallery = gallery;
        gallery.getArtworkList().add(this);
    }
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getArtworkList().remove(this);
        }
        this.member = member;
        member.getArtworkList().add(this);
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public void setLiked(boolean liked) {
        this.liked = liked;
    }


    public void setStatus(ArtworkStatus status) {
        this.status = status;

    }

    public void modify(Artwork artwork) {

        Optional.ofNullable(artwork.getImagePath())
                .ifPresent(imagePath -> this.imagePath = imagePath);
        Optional.ofNullable(artwork.getTitle())
                .ifPresent(title -> this.title = title);
        Optional.ofNullable(artwork.getContent())
                .ifPresent(content -> this.content = content);
    }

    /* ################### 생성자 ################### */
    @Builder
    public Artwork(String title, String content, MultipartFile image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    // 기본 Test용 생성자
    public Artwork(Long artworkId) {
        this.artworkId = artworkId;
        this.title = "test_title";
        this.content = "test_content";
        this.imagePath = "/";
        super.createdAt = LocalDateTime.now();
    }
    // RepositoryTest용 생성자
    public Artwork(Long artworkId, int likeCount) {
        this(artworkId);
        // 테스트할 때 필요한 데이터
        this.likeCount = likeCount;
    }

    /* ################### toDto ################### */
    public ArtworkResponseDto toArtworkResponseDto() {
        return ArtworkResponseDto.builder()
                .artworkId(getArtworkId())
                .memberId(getMemberId())
                .nickName(member.getNickname())
                .title(getTitle())
                .content(getContent())
                .imagePath(getImagePath())
                .likeCount(getLikeCount())
                .liked(isLiked())
                .commentCount(getCommentCount())
                .build();
    }

    public OneYearFourCutResponseDto toOneYearFourCutResponseDto() {
        return OneYearFourCutResponseDto.builder()
                .artworkId(getArtworkId())
                .imagePath(getImagePath())
                .likeCount(getLikeCount())
                .build();
    }
    public AlarmEvent toAlarmEvent(Long receiverId) {
        return AlarmEvent.builder()
                .receiverId(receiverId)
                .senderId(this.getMember().getMemberId())
                .alarmType(AlarmType.POST_ARTWORK)
                .galleryId(this.getGallery().getGalleryId())
                .artworkId(this.getArtworkId())
                .build();
    }
}
