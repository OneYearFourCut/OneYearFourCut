package com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PrincipalDto { //principal에 email과 id값을 담기위해 만든 dto클래스
    private String email;
    private Long id;
}
