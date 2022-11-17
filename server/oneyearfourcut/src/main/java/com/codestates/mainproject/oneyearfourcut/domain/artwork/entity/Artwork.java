package com.codestates.mainproject.oneyearfourcut.domain.artwork.entity;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.vote.entity.Vote;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Artwork extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artworkId;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 255, nullable = false)
    private String imgPath;

    @Transient
    private MultipartFile img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GALLERY_ID")
    private Gallery gallery;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "artwork")
    private List<Vote> voteList = new ArrayList<>();

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




}
