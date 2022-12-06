package com.codestates.mainproject.oneyearfourcut.domain.refreshToken.service;

import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.refreshToken.entity.RefreshToken;
import com.codestates.mainproject.oneyearfourcut.domain.refreshToken.repository.RefreshTokenRepository;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken saveRefreshToken(Member member, String refreshToken) {
        return refreshTokenRepository.save(RefreshToken.builder()
                .member(member)
                .token(refreshToken)
                .build());
    }

    @Transactional(readOnly = true)
    public RefreshToken findRefreshTokenByEmail(String email) {
        return refreshTokenRepository.findByMember_Email(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public boolean isTokenExist(String email) {
        return refreshTokenRepository.existsByMember_Email(email);
    }

    public void updateRefreshToken(String email, String refreshToken) {
        RefreshToken token = findRefreshTokenByEmail(email);
        token.updateToken(refreshToken);
    }

    public void deleteRefreshToken(Long memberId) {
        refreshTokenRepository.deleteByMember_MemberId(memberId);
    }
}
