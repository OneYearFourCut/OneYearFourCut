package com.codestates.mainproject.oneyearfourcut.global.exception.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(400, "Member를 찾을 수 없습니다"),
    COMMENT_NOT_FOUND(400, "해당 댓글을 찾을 수 없습니다");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
