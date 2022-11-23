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


    //회원 수정
    @PatchMapping("/me")
    public ResponseEntity patchMember() {
        Long memberId = 1L;

        //로직
        return null;
    }

    //회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity deleteMember() {
        Long memberId = 1L; //jwt로 memberId 찾아와야함

        memberService.deleteMember(memberId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
