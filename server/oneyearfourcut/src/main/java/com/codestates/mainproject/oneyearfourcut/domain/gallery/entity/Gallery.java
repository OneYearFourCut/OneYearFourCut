package com.codestates.mainproject.oneyearfourcut.domain.gallery.entity;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private GalleryStatus status = GalleryStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "gallery")
    private List<Artwork> artworkList = new ArrayList<>();

    @OneToMany(mappedBy = "gallery")
    private List<Comment> commentList = new ArrayList<>();

}