package com.codestates.mainproject.oneyearfourcut.domain.member.controller;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.PrincipalDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean({JpaMetamodelMappingContext.class, ClientRegistrationRepository.class})
@AutoConfigureRestDocs
class MemberControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private GalleryService galleryService;

    @TestConfiguration
    static class testSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf().disable()
                    .build();
        }
    }
    @BeforeAll
    public static void setup() {
        //security context holder
        String username = "test";
        long id = 1L;
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(new PrincipalDto(username, id), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String jwt = "Bearer test1234test1234";
    private Member member = Member.builder()
            .nickname("?????????")
            .profile("http://testprofile")
            .build();

    @Test
    void getMember() throws Exception {
        //given
        given(memberService.findMember(anyLong()))
                .willReturn(member);


        ResultActions actions = mockMvc.perform(
                get("/members/me")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        actions.andExpect(status().isOk())
                .andDo(document(
                        "getMember",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT - Access Token")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("profile").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                        fieldWithPath("galleryId").type(JsonFieldType.NULL).description("?????? ????????? ?????????(????????? null, ????????? ??????)")
                                )
                        )
                ));
    }

    @Test
    void patchMember() throws Exception {
        //given
        // member ??????
        given(memberService.modifyMember(anyLong(), any(MemberRequestDto.class)))
                .willReturn(member.toMemberResponseDto());

        MockMultipartFile file = new MockMultipartFile("profile", "profile", "image/jpeg",
                "file".getBytes());

        //when
        ResultActions actions = mockMvc.perform(
                multipart("/members/me")
                        .file(file)
                        .header("Authorization", jwt)
                        .param("nickname", "????????? ??????")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("?????? ?????? ??????"))
                .andDo(document(
                        "patchMember",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT - Access Token")
                                )
                        ),
                        requestParts(
                                List.of(
                                        partWithName("profile").description("????????? ?????????")
                                )
                        ),
                        requestParameters(
                                List.of(
                                        parameterWithName("nickname").description("??????")
                                )
                        )
                ));
    }

    @Test
    void deleteMember() throws Exception {
        //given
        willDoNothing().given(memberService).deleteMember(anyLong());
        willDoNothing().given(galleryService).deleteGallery(anyLong());


        //when
        ResultActions actions = mockMvc.perform(
                delete("/members/me")
                        .header("Authorization", jwt)
        );

        //then
        actions.andExpect(status().isNoContent())
                .andExpect(jsonPath("$").value("?????? ?????? ??????"))
                .andDo(document(
                        "deleteMember",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT - Access Token")
                                )
                        )
                ));
    }
}