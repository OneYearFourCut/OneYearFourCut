package com.codestates.mainproject.oneyearfourcut.domain.Like.entity;

import lombok.Getter;

public enum LikeStatus {
    LIKE("등록"),
    CANCEL("취소")

    @Getter
    private String status;

    LikeStatus(String status) {
        this.status = status;
    }
}
