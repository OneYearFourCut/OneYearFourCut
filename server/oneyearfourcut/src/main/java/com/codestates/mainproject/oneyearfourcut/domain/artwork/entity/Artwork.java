package com.codestates.mainproject.oneyearfourcut.domain.artwork.entity;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.vote.entity.Vote;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artwork extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artworkId;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 255, nullable = false)
    private String imagePath;

    @Transient
    private MultipartFile img;

    // <============================
    // Artwork Repository에서 voteCount를 사용하기 위해 Formula 추가 (JPQL 대신)
    @Formula("(select count(*) from vote v where v.artwork_id = artwork_id)")
    private int likeCount;

    @Formula("(select count(*) from comment c where c.artwork_id = artwork_id)")
    private int commentCount;

    // <=== 좋아요 로직에 따라 변경될 수 있음.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GALLERY_ID")
    private Gallery gallery;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.REMOVE)
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


    // ReplyMapper - Response에 memberId 담으려면 아래 getter를 추가해야 함... (toEntity 고려해봐야 할까요...)
    public Long getMemberId() {
        return this.member.getMemberId();
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "artworkId=" + artworkId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgPath='" + imagePath + '\'' +
                ", createdAt='" + this.getCreatedAt() + '\'' +
                '}';
    }
}
