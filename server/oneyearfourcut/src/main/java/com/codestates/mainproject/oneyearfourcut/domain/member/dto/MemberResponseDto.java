package com.codestates.mainproject.oneyearfourcut.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {
    private String nickname;
    private String profile;
}
