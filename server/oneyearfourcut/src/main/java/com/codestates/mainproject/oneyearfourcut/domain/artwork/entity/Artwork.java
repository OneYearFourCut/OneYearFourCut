package com.codestates.mainproject.oneyearfourcut.domain.artwork.entity;


import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.OneYearFourCutResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
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

    @Column(length = 30, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String imagePath;

    @Enumerated(EnumType.STRING)
    private ArtworkStatus status;

    @Transient
    private MultipartFile image;

    @Formula("(select count(*) from artwork_like v where v.artwork_id = artwork_id)")
    private int likeCount;
    @Transient
    private boolean liked;
    @Formula("(select count(*) from comment c where c.artwork_id = artwork_id)")
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
        return this.getArtworkLikeList().size();
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
        // ************************* S3 설정 시 이미지 관련 변경 예정 *************************
        Optional.ofNullable(artwork.getImage())
                .ifPresent(image -> this.imagePath = "/" + image.getOriginalFilename());
        Optional.ofNullable(artwork.getTitle())
                .ifPresent(title -> this.title = title);
        Optional.ofNullable(artwork.getContent())
                .ifPresent(content -> this.content = content);
    }

    /* ################### 생성자 ################### */
    @Builder
    public Artwork(Long artworkId, String title, String content, String imagePath, MultipartFile image) {
        this.artworkId = artworkId;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.image = image;
        this.artworkLikeList = new ArrayList<>();

    }

    /* ################### toDto ################### */
    public ArtworkResponseDto toArtworkResponseDto() {
        return ArtworkResponseDto.builder()
                .artworkId(getArtworkId())
                .memberId(getMemberId())
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

}
