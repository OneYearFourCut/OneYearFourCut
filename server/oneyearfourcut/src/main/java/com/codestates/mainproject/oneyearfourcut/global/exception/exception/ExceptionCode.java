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
    ARTWORK_DELETED(400, "삭제된 작품입니다."),
    UNAUTHORIZED(401, "접근 권한이 없습니다."),
    REPLY_NOT_FOUND_FROM_COMMENT(400, "해당 답글이 댓글에 존재하지 않습니다."),
    COMMENT_NOT_FOUND_FROM_GALLERY(400, "해당 댓글이 전시관에 존재하지 않습니다." ),
    IMAGE_NOT_FOUND_FROM_REQUEST(400, "요청에 이미지를 넣어주세요."),
    //토큰 관련
    EXPIRED_ACCESS_TOKEN(456, "expired Access Token"),
    TRY_LOGIN(457, "로그인이 필요합니다."),
    WRONG_ACCESS_TOKEN(400, "잘못된 Access Token 입니다."),
    NO_ACEESS_TOKEN(400, "Access Token이 header에 없습니다."),
    NO_REFRESH_TOKEN(400, "Refresh Token이 header에 없습니다."),
    //파일 관련
    EXCEEDED_FILE_SIZE(400, "파일 사이즈가 10MB를 초과하였습니다."),
    INVALID_FILE_TYPE(400, "잘못된 파일명입니다."),
    UNSUPPORTED_FILE_EXTENSION(400, "지원하지 않는 확장자입니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
