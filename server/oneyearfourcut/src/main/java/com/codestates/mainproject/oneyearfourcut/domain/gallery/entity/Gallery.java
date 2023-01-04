package com.codestates.mainproject.oneyearfourcut.domain.gallery.entity;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.follow.entity.Follow;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryPostResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter //테스트코드에서 gallery객체 만들때 setter 사용하는 분이 있어서 등록. 나중에 제거예정
@NoArgsConstructor
public class Gallery extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long galleryId;

    @Column(length = 15, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private GalleryStatus status;

    @Formula("(select count(*) from follow f where f.member_id = member_id)")
    private Long followingCount;

    @Formula("(select count(*) from follow f where f.gallery_id = gallery_id) ")
    private Long followerCount;

    @Builder
    public Gallery(String title, String content, GalleryStatus status, Member member, Long followingCount, Long followerCount) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.member = member;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }

    //jpa 연관관계 맵핑 위해 생성하는 member 엔티티 용 생성자
    public Gallery(Long galleryId) {
        this.galleryId = galleryId;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }
    public void updateStatus(GalleryStatus status) {
        this.status = status;
    }

    public void generateTestGallery(Long galleryId, LocalDateTime createdAt) {
        this.galleryId = galleryId;
        this.createdAt = createdAt;
    }

    public GalleryResponseDto toGalleryResponseDto() {
        return GalleryResponseDto.builder()
                .galleryId(this.galleryId)
                .title(this.title)
                .content(this.content)
                .memberId(this.member.getMemberId())
                .createdAt(this.getCreatedAt())
                .followingCount(this.getFollowingCount())
                .followerCount(this.getFollowerCount())
                .build();
    }
    public GalleryPostResponseDto toGalleryPostResponseDto() {
        return GalleryPostResponseDto.builder()
                .galleryId(this.galleryId)
                .title(this.title)
                .content(this.content)
                .createdAt(this.getCreatedAt())
                .build();
    }

    // Artwork에서 갤러리를 호출할 때마다 member쪽이 조회 쿼리문이 발생하여 지연 로딩으로 바꿨습니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "gallery")
    private List<Artwork> artworkList = new ArrayList<>();

    @OneToMany(mappedBy = "gallery", cascade = CascadeType.REMOVE, targetEntity = Comment.class)
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Comment> commentList = new ArrayList<>();


    // 갤러리 closed 상태변경 시 follow 삭제하기위해서 추가하였습니다.
    @OneToMany(mappedBy = "gallery", cascade = CascadeType.REMOVE, targetEntity = Follow.class)
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Follow> followList = new ArrayList<>();


}