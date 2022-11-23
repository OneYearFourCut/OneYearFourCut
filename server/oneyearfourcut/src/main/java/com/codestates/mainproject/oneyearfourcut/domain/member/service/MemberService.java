package com.codestates.mainproject.oneyearfourcut.domain.member.service;

import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void createMember(Member postMember) {
        if (!memberRepository.findByEmail(postMember.getEmail()).isPresent()) {
            memberRepository.save(postMember);
        }
    }

    public Member findMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Member findMemberByEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        return optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public void deleteMember(Long memberId) {
        findMember(memberId);   //회원이 존재하는지 확인
        //상태변화로 변경 예정 -> 삭제에서 활동으로 바뀔때의 로직 oauth쪽에 적용 시켜야함
        memberRepository.deleteById(memberId);
    }
}