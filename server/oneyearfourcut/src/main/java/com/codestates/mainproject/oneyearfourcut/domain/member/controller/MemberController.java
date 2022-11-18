package com.codestates.mainproject.oneyearfourcut.domain.member.controller;

import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.mapper.MemberMapper;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;

    //회원 등록
    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberRequestDto memberRequestDto) {
        //Oauth 2.0 카카오 회원가입이면 MemberRequestDto로 받아오는 형식은 아닐 것 같은데, 일단 일반적인 회원가입처럼 구현
        Member postMember = memberMapper.memberRequestDtoToMember(memberRequestDto);
        memberService.createMember(postMember);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    //회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity deleteMember() {
        Long memberId = 1L; //jwt로 memberId 찾아와야함

        memberService.deleteMember(memberId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
