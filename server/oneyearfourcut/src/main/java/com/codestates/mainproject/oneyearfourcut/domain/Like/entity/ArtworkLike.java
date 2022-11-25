package com.codestates.mainproject.oneyearfourcut.domain.Like.entity;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ArtworkLike extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ARTWORK_ID")
    private Artwork artwork;

    public void setMember(Member member) {
        if (this.member != null) {
            member.getArtworkLikeList().remove(this);
        }
        this.member = member;
        member.getArtworkLikeList().add(this);
    }

    public void setArtwork(Artwork artwork) {
        if (this.artwork != null) {
            this.artwork.getArtworkLikeList().remove(this);
        }
        this.artwork = artwork;
        artwork.getArtworkLikeList().add(this);
    }

    public ArtworkLike(Long voteId) {
        this.voteId = voteId;
    }
}
