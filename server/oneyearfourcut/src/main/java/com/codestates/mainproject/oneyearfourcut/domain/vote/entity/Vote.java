package com.codestates.mainproject.oneyearfourcut.domain.vote.entity;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.ArtWork;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;

import javax.persistence.*;

@Entity
public class Vote extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ARTWORK_ID")
    private ArtWork artWork;
}
