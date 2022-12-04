package com.codestates.mainproject.oneyearfourcut.domain.member.controller;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Role;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.aws.service.AwsS3Service;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class MemberControllerRestDocsTest {
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
    @MockBean
    private AwsS3Service awsS3Service;

    @Test
    void getMember() throws Exception {
        //given
        // member 등록
        Member member = memberRepository.save(Member.builder()
                .nickname("홍길동")
                .email("kang1@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(MemberStatus.ACTIVE)
                .build());
        galleryRepository.save(Gallery.builder()
                .status(GalleryStatus.OPEN)
                .member(member)
                .title("Entity에")
                .content("컬럼 nullable이 false여서 에러남")
                .build());

        //해당 member jwt 생성
        String jwt = jwtTokenizer.testJwtGenerator(member);

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
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("profile").type(JsonFieldType.STRING).description("프로필 이미지 경로"),
                                        fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("오픈 전시관 식별자(없으면 null)")
                                )
                        )
                ));
    }

    @Test
    void patchMember() throws Exception {
        //given
        // member 등록
        Member member = memberRepository.save(Member.builder()
                .nickname("홍길동")
                .email("kang2@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(MemberStatus.ACTIVE)
                .build());

        //해당 member jwt 생성
        String jwt = jwtTokenizer.testJwtGenerator(member);

        MockMultipartFile file = new MockMultipartFile("profile", "profile", "image/jpeg",
                "file".getBytes());

        //S3 이미지 업로드 given 처리
        given(awsS3Service.uploadFile(any(MultipartFile.class)))
                .willReturn("https://test/1234.png");

        //when
        ResultActions actions = mockMvc.perform(
                multipart("/members/me")
                        .file(file)
                        .header("Authorization", jwt)
                        .param("nickname", "수정된 이름")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("회원 수정 성공"))
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
                                        partWithName("profile").description("프로필 이미지")
                                )
                        ),
                        requestParameters(
                                List.of(
                                        parameterWithName("nickname").description("이름")
                                )
                        )
                ));
    }

    @Test
    void deleteMember() throws Exception {
        //given
        // member 등록
        Member member = memberRepository.save(Member.builder()
                .nickname("홍길동")
                .email("kang3@gmail.com")
                .profile("/path")
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .build());

        //해당 member jwt 생성
        String jwt = jwtTokenizer.testJwtGenerator(member);

        //when
        ResultActions actions = mockMvc.perform(
                delete("/members/me")
                        .header("Authorization", jwt)
        );

        //then
        actions.andExpect(status().isNoContent())
                .andExpect(jsonPath("$").value("회원 탈퇴 성공"))
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