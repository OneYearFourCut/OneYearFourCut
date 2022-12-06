package com.codestates.mainproject.oneyearfourcut.domain.artwork.entity;

import lombok.Getter;

public enum ArtworkStatus {

    REGISTRATION("등록"),
    DELETED("삭제");

    @Getter
    private String status;

    ArtworkStatus(String status) {
        this.status = status;
    }
}
