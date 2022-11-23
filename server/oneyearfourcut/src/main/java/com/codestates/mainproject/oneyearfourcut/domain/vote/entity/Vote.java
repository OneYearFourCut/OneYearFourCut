package com.codestates.mainproject.oneyearfourcut.domain.vote.entity;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Vote extends Auditable {
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
            member.getVoteList().remove(this);
        }
        this.member = member;
        member.getVoteList().add(this);
    }

    public void setArtwork(Artwork artwork) {
        if (this.artwork != null) {
            this.artwork.getVoteList().remove(this);
        }
        this.artwork = artwork;
        artwork.getVoteList().add(this);
    }

}
