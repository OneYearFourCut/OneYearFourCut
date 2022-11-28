package com.codestates.mainproject.oneyearfourcut.domain.gallery.controller;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Role;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class GalleryControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GalleryRepository galleryRepository;


    @Autowired
    private Gson gson;

    @Test
    void postGallery() throws Exception {
        //given
        //test전 member 등록
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("test1@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(MemberStatus.ACTIVE)
                .build());

        //해당 member jwt 생성
        String jwt = jwtTokenizer.testJwtGenerator(member);

        //test하려는 gallery request
        GalleryRequestDto requestDto = GalleryRequestDto.builder()
                .title("나의 전시관")
                .content("안녕하세요")
                .build();
        String content = gson.toJson(requestDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/galleries")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
//                        .with(csrf())
        );


        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(requestDto.getTitle()))
                .andExpect(jsonPath("$.content").value(requestDto.getContent()))
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
        //Gallery 저장
        Gallery gallery = galleryRepository.save(Gallery.builder()
                .title("나의 전시관")
                .content("안녕하세요")
                .build());

        //when
        ResultActions actions = mockMvc.perform(
                get("/galleries/{galleryId}", gallery.getGalleryId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(gallery.getTitle()))
                .andExpect(jsonPath("$.content").value(gallery.getContent()))
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
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("전시관 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("전시관 내용"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자")
                                )
                        )
                ));
    }

    @Test
    void patchGallery() throws Exception {
        //given
        // member 등록
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("test1@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(MemberStatus.ACTIVE)
                .build());

        //해당 member jwt 생성
        String jwt = jwtTokenizer.testJwtGenerator(member);

        //member 의 Gallery 저장
        Gallery gallery = galleryRepository.save(Gallery.builder()
                .title("나의 전시관")
                .content("안녕하세요")
                .member(member)
                .status(GalleryStatus.OPEN)
                .build());

        //test하려는 gallery request
        GalleryRequestDto requestDto = GalleryRequestDto.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();
        String content = gson.toJson(requestDto);

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
        // member 등록
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("test1@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(MemberStatus.ACTIVE)
                .build());

        //해당 member jwt 생성
        String jwt = jwtTokenizer.testJwtGenerator(member);

        //member 의 Gallery 저장
        Gallery gallery = galleryRepository.save(Gallery.builder()
                .title("나의 전시관")
                .content("안녕하세요")
                .member(member)
                .status(GalleryStatus.OPEN)
                .build());

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