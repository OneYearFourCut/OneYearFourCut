package com.codestates.mainproject.oneyearfourcut.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MemberRequestDto { //회원 수정용 requstDto
    private String nickname;
    private MultipartFile profile;

    @Builder    //테스트 코드용 생성자
    public MemberRequestDto(String nickname, MultipartFile profile) {
        this.nickname = nickname;
        this.profile = profile;
    }
}
