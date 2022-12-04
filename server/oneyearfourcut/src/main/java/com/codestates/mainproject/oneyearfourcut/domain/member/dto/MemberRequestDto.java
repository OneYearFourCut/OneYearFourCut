package com.codestates.mainproject.oneyearfourcut.domain.member.dto;

import com.codestates.mainproject.oneyearfourcut.global.validator.NotSpace;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberRequestDto { //회원 수정용 requstDto
    @NotSpace(message = "닉네임은 필수 입력 값입니다.")
    @Size(min = 1, max = 8)
    private String nickname;
    private MultipartFile profile;

    @Builder    //테스트 코드용 생성자
    public MemberRequestDto(String nickname, MultipartFile profile) {
        this.nickname = nickname;
        this.profile = profile;
    }
}
