package com.codestates.mainproject.oneyearfourcut.domain.gallery.controller;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryPatchDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryPostResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.PrincipalDto;
import com.google.gson.Gson;
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

import java.time.LocalDateTime;
import java.util.List;

import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GalleryController.class)
@MockBean({JpaMetamodelMappingContext.class, ClientRegistrationRepository.class})
@AutoConfigureRestDocs
class GalleryControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GalleryService galleryService;
    @Autowired
    private Gson gson;


    private String jwt = "Bearer test1234test1234";
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

    @Test
    void postGallery() throws Exception {
        //given
        LocalDateTime time = LocalDateTime.of(2022, 12, 25, 12, 25, 25);

        String content = gson.toJson(GalleryRequestDto.builder()
                .title("홍길동의 전시회")
                .content("안녕하세요")
                .build());

        GalleryPostResponseDto responseDto = GalleryPostResponseDto.builder()
                .galleryId(1L)
                .title("홍길동의 전시회")
                .content("안녕하세요")
                .createdAt(time)
                .build();

        given(galleryService.createGallery(any(GalleryRequestDto.class), anyLong()))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/galleries")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.galleryId").value(responseDto.getGalleryId()))
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.createdAt").value(String.valueOf(responseDto.getCreatedAt())))
                .andDo(document(
                        "postGallery",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT - Access Token")
                                )
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("전시관 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("전시관 내용")
                                )
                        )
                        , responseFields(
                                List.of(
                                        fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("전시관 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("전시관 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("전시관 내용"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자")
                                )
                        )
                ));
    }

    @Test
    void getGallery() throws Exception {
        //given
        LocalDateTime time = LocalDateTime.of(2022, 12, 25, 12, 25, 25);
        Member member = new Member(1L);
        member.updateProfile("/profile");
        Gallery gallery = Gallery.builder()
                .title("홍길동의 전시회")
                .content("안녕하세요")
                .followerCount(23L)
                .followingCount(31L)
                .member(member)
                .build();
        gallery.generateTestGallery(1L, time);

        given(galleryService.findGallery(1L))
                .willReturn(gallery);

        //when
        ResultActions actions = mockMvc.perform(
                get("/galleries/{galleryId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.galleryId").value(gallery.getGalleryId()))
                .andExpect(jsonPath("$.title").value(gallery.getTitle()))
                .andExpect(jsonPath("$.content").value(gallery.getContent()))
                .andExpect(jsonPath("$.createdAt").value(String.valueOf(gallery.getCreatedAt())))
                .andDo(document(
                        "getGallery",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("galleryId").description("Gallery 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("전시관 식별자"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("전시관 주인 식별자"),
                                        fieldWithPath("profile").type(JsonFieldType.STRING).description("전시관 주인 프로필"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("전시관 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("전시관 내용"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                        fieldWithPath("followingCount").type(JsonFieldType.NUMBER).description("팔로잉 수"),
                                        fieldWithPath("followerCount").type(JsonFieldType.NUMBER).description("팔로워 수")
                                )
                        )
                ));
    }

    @Test
    void patchGallery() throws Exception {
        //given
        String content = gson.toJson(GalleryPatchDto.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build());

        GalleryResponseDto galleryResponseDto = Gallery.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .member(new Member(1L))
                .build()
                .toGalleryResponseDto();

        given(galleryService.modifyGallery(any(GalleryPatchDto.class), anyLong()))
                .willReturn(galleryResponseDto);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/galleries/me")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("전시관 수정 성공"))
                .andDo(document(
                        "patchGallery",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT - Access Token")
                                )
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("전시관 제목 (생략 가능)"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("전시관 내용 (생략 가능)")
                                )
                        )
                ));
    }

    @Test
    void deleteGallery() throws Exception {
        //given
        willDoNothing().given(galleryService).deleteGallery(anyLong());

        //when
        ResultActions actions = mockMvc.perform(
                delete("/galleries/me")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isNoContent())
                .andExpect(jsonPath("$").value("전시관 삭제 성공"))
                .andDo(document(
                        "deleteGallery",
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