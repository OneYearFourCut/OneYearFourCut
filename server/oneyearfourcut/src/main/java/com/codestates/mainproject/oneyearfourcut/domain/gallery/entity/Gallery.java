package com.codestates.mainproject.oneyearfourcut.domain.gallery.entity;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import lombok.*;

import javax.persistence.*;
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

    @Builder
    public Gallery(String title, String content, GalleryStatus status, Member member) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.member = member;
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

    public GalleryResponseDto toGalleryResponseDto() {
        return GalleryResponseDto.builder()
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

}