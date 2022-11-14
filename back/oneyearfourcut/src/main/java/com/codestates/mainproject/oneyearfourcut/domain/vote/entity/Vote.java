package com.codestates.mainproject.oneyearfourcut.domain.vote.entity;

import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vote extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private Long artworkId;
    private Long memberId;
}
