package com.codestates.mainproject.oneyearfourcut.domain.refreshToken.controller;

import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Role;
import com.codestates.mainproject.oneyearfourcut.domain.refreshToken.entity.RefreshToken;
import com.codestates.mainproject.oneyearfourcut.domain.refreshToken.service.RefreshTokenService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.PrincipalDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RefreshTokenController.class)
@MockBean({JpaMetamodelMappingContext.class, ClientRegistrationRepository.class, JwtTokenizer.class})
@AutoConfigureRestDocs
class RefreshTokenControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    public void setup() {
        //security context holder
        String username = "test";
        long id = 1L;
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(new PrincipalDto(username, id), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void 토큰_재발급_테스트() throws Exception {
        //given
        String jwt = "eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjoiVVNFUiIsImlkIjo5LCJ1c2VybmFtZSI6InRlc3QxQGdtYWlsLmNvbSIsInN1YiI6InRlc3QxQGdtYWlsLmNvbSIsImlhdCI6MTY2OTgyNzA2OCwiZXhwIjoxNjY5ODI3MTI4fQ.xNAhFxDcKKHGqvDfh7LQhtJyGzqq2mbjC30adPqPGLzVhmmGlze17Zbaue1LrgxO'";
        Member member = Member.builder()
                .email("test@gmail.com")
                .role(Role.USER)
                .build();

        RefreshToken token = RefreshToken.builder()
                .member(member)
                .token(jwt)
                .build();
        Claims claims = Jwts.claims().setSubject("test@gmail.com");

        given(jwtTokenizer.getSecretKey())
                .willReturn("test");
        given(jwtTokenizer.encodeBase64SecretKey(anyString()))
                .willReturn("test");
        given(jwtTokenizer.expiredTokenClaims(anyString(), anyString()))
                .willReturn(claims);
        given(refreshTokenService.findRefreshTokenByEmail(anyString()))
                .willReturn(token);
        given(jwtTokenizer.generateAccessToken(any(Claims.class), anyString(), anyString()))
                .willReturn("Bearer " + jwt);
        given(jwtTokenizer.generateRefreshToken(anyString(), anyString()))
                .willReturn(jwt);
        given(jwtTokenizer.isExpiredToken(anyString(), anyString()))
                .willReturn(false);

        //when
        ResultActions actions = mockMvc.perform(
                get("/auth/refresh")
                        .header("Authorization", "Bearer " + jwt)
                        .header("refresh", jwt)
        );

        //then
        actions.andExpect(status().isCreated())
                .andDo(document("refreshToken",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT - 만료된 Access Token"),
                                        headerWithName("refresh").description("JWT - Refresh Token")
                                )
                        ),
                        responseHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT - 새로 발급된 Access Token"),
                                        headerWithName("refresh").description("JWT - 새로 발급된 Refresh Token")
                                )
                        )
                ));
    }

    @Test
    void 로그아웃_테스트() throws Exception {
        //given
        String accessToken = "Bearer eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjoiVVNFUiIsImlkIjo5LCJ1c2VybmFtZSI6InRlc3QxQGdtYWlsLmNvbSIsInN1YiI6InRlc3QxQGdtYWlsLmNvbSIsImlhdCI6MTY2OTgyNzA2OCwiZXhwIjoxNjY5ODI3MTI4fQ.xNAhFxDcKKHGqvDfh7LQhtJyGzqq2mbjC30adPqPGLzVhmmGlze17Zbaue1LrgxO'";
        willDoNothing().given(refreshTokenService).deleteRefreshToken(anyLong());

        ResultActions actions = mockMvc.perform(
                get("/auth/logout")
                        .header("Authorization", accessToken)
        );

        actions.andExpect(status().isNoContent())
                .andDo(document("logout",
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