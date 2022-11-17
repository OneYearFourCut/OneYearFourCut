package com.codestates.mainproject.oneyearfourcut.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberRequestDto {    //Oauth2.0 카카오 회원가입으로 변경시 없어질 듯
    private String email;
    private String nickname;
}
