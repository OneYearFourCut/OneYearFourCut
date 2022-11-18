package com.codestates.mainproject.oneyearfourcut.domain.member.mapper;

import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberRequestDtoToMember(MemberRequestDto memberRequestDto);

}
