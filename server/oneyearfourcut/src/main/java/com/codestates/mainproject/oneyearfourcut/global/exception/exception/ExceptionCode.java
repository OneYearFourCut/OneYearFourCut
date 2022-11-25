package com.codestates.mainproject.oneyearfourcut.global.exception.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(400, "Member를 찾을 수 없습니다"),
    GALLERY_NOT_FOUND(400, "해당 전시관을 찾을 수 없습니다."),
    OPEN_GALLERY_EXIST(400, "오픈된 전시관이 이미 존재합니다."),
    CLOSED_GALLERY(400, "폐관된 전시관입니다."),
    ARTWORK_NOT_FOUND(400, "작품이 존재하지 않습니다."),
    ARTWORK_NOT_FOUND_FROM_GALLERY(400, "해당 작품이 전시관에 존재하지 않습니다."),
    COMMENT_NOT_FOUND(400, "댓글이 존재하지 않습니다."),
    COMMENT_DELETED(400, "이미 삭제된 댓글입니다."),
    UNAUTHORIZED(401, "접근 권한이 없습니다."),
    REPLY_NOT_FOUND_FROM_COMMENT(400, "해당 답글이 댓글에 존재하지 않습니다."),
    COMMENT_NOT_FOUND_FROM_GALLERY(400, "해당 댓글이 전시관에 존재하지 않습니다." ),
    NO_AUTHORITY(400, "해당 작업의 권한이 없습니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
