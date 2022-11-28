package com.codestates.mainproject.oneyearfourcut.domain.member.controller;

import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //회원 조회
    @GetMapping("/me")
    public ResponseEntity getMember(@LoginMember Long memberId) {
        Member member = memberService.findMember(memberId);

        return new ResponseEntity(member.toMemberResponseDto(), HttpStatus.OK);
    }
    //회원 수정
    @PostMapping("/me") //form-data는 post요청으로 해야함(?)
    public ResponseEntity patchMember(@LoginMember Long memberId,
                                      @ModelAttribute MemberRequestDto memberRequestDto) {
        //회원 수정 시에 프로필, 이름을 한번에 변경할건지 프론트와 의논해봐야함
        MemberResponseDto memberResponseDto = memberService.modifyMember(memberId, memberRequestDto);

        return new ResponseEntity("회원 수정 성공", HttpStatus.OK);
    }

    //회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity deleteMember(@LoginMember Long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity("회원 탈퇴 성공", HttpStatus.NO_CONTENT);
    }
}
