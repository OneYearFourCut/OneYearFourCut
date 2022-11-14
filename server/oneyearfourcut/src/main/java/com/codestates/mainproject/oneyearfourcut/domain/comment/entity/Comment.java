package com.codestates.mainproject.oneyearfourcut.domain.comment.entity;


import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private Long galleryId;
    private Long memberId;
    private String content;
    private Long artworkId;
}
