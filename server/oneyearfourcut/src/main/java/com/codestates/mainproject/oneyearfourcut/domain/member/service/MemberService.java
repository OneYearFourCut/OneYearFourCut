package com.codestates.mainproject.oneyearfourcut.domain.member.service;

import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.aws.service.AwsS3Service;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final AwsS3Service awsS3Service;

    public void createMember(Member postMember) {   //Oauth Kakao 로그인 시 회원가입 진행
        if (!memberRepository.findByEmail(postMember.getEmail()).isPresent()) {
            memberRepository.save(postMember);
        }
    }

    public MemberResponseDto modifyMember(Long memberId, MemberRequestDto memberRequestDto) {
        Member findMember = findMember(memberId);

        Optional.ofNullable(memberRequestDto.getNickname())
                        .ifPresent(findMember::updateNickname);
        Optional<MultipartFile> multipartFile = Optional.ofNullable(memberRequestDto.getProfile());

        if (multipartFile.isPresent() && !multipartFile.get().isEmpty()) {
            //이미지 저장하고, 해당 경로를 findMember에 넣어주는 로직
            String profile = findMember.getProfile();
            findMember.setProfile(awsS3Service.uploadFile(multipartFile.get()));
            if (!profile.contains("kakaocdn.net")) {
                awsS3Service.deleteImage(profile);
            }
        }


        Member savedMember = memberRepository.save(findMember);

        return savedMember.toMemberResponseDto();
    }

    @Transactional(readOnly = true)
    public Member findMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        return optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public void deleteMember(Long memberId) {
        Member findMember = findMember(memberId);//회원이 존재하는지 확인

        findMember.updateStatus(MemberStatus.DELETE);
    }
}