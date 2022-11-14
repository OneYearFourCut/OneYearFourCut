package com.codestates.mainproject.oneyearfourcut.domain.member.repository;

import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
