package com.codestates.mainproject.oneyearfourcut.domain.member.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MemberRequestDto {    //Oauth2.0 카카오 회원가입으로 변경시 없어질 듯
    private String nickname;
    private MultipartFile profile;
}
