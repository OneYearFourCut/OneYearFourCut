package com.codestates.mainproject.oneyearfourcut.domain.member.service;

import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(Member postMember) {
        //이미 존재하는 회원인지 확인하는 로직 -> oauth로 바뀌면 없을듯?
        Member savedMember = memberRepository.save(postMember);

        return savedMember;
    }

    public Member findMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public void deleteMember(Long memberId) {
        findMember(memberId);   //회원이 존재하는지 확인
        memberRepository.deleteById(memberId);
    }
}