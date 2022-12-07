package com.codestates.mainproject.oneyearfourcut.domain.refreshToken.controller;

import com.codestates.mainproject.oneyearfourcut.domain.refreshToken.service.RefreshTokenService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/auth/refresh")
    public ResponseEntity refreshAccessToken(HttpServletResponse response, HttpServletRequest request) {
        // 검증하고 재발급 하는 로직?
        String accessToken = Optional.ofNullable(request.getHeader("Authorization"))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_ACEESS_TOKEN)).replace("Bearer ", "");
        String refreshToken = Optional.ofNullable(request.getHeader("refresh"))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_REFRESH_TOKEN));
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        //만료된 토큰인지 확인
        Claims expiredTokenClaims = Optional.ofNullable(jwtTokenizer.expiredTokenClaims(accessToken, base64EncodedSecretKey))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.WRONG_ACCESS_TOKEN));
        String expiredTokenEmail = expiredTokenClaims.getSubject();

        String token = refreshTokenService.findRefreshTokenByEmail(expiredTokenEmail).getToken();
        if (!token.equals(refreshToken) || jwtTokenizer.isExpiredToken(refreshToken, base64EncodedSecretKey)) {
            //두 토큰의 소유자가 다른 경우, 리프레시 토큰이 만료된 경우
            throw new BusinessLogicException(ExceptionCode.TRY_LOGIN);
        }

        //검증에 통과한다면 새로운 access, refresh token 발행
        String newAccessToken = jwtTokenizer.generateAccessToken(expiredTokenClaims, expiredTokenEmail, base64EncodedSecretKey);
        String newRefreshToken = jwtTokenizer.generateRefreshToken(expiredTokenEmail, base64EncodedSecretKey);
        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.setHeader("refresh", newRefreshToken);

        //리프레시 토큰 다시 저장
        refreshTokenService.updateRefreshToken(expiredTokenEmail, newRefreshToken);

        //결과 리턴
        return new ResponseEntity("새로 토큰이 발급되었습니다.", HttpStatus.CREATED);
    }

    @GetMapping("/auth/logout")
    public ResponseEntity logout(@LoginMember Long memberId) {
        refreshTokenService.deleteRefreshToken(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
