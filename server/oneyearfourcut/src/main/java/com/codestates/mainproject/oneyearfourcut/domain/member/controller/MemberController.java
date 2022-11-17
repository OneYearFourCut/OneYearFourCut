package com.codestates.mainproject.oneyearfourcut.domain.member.controller;

import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    //회원 등록
    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberRequestDto memberRequestDto) {
        //Oauth 2.0 카카오 회원가입이면 MemberRequestDto로 받아오는 형식은 아닐 것 같은데, 일단 일반적인 회원가입처럼 구현

        return new ResponseEntity(HttpStatus.CREATED);
    }

    //회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity deleteMember() {
        //jwt를 통해 memberId 알아내서 삭제 처리
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
