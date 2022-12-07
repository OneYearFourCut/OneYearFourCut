package com.codestates.mainproject.oneyearfourcut.domain.refreshToken.repository;

import com.codestates.mainproject.oneyearfourcut.domain.refreshToken.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember_Email(String email);
    void deleteByMember_MemberId(Long memberId);

    Boolean existsByMember_Email(String email);
}
