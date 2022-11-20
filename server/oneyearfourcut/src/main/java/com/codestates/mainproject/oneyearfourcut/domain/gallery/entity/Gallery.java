package com.codestates.mainproject.oneyearfourcut.domain.gallery.entity;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gallery extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long galleryId;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING) //enum의 이름을 컬럼에 저장
    private GalleryStatus status;

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