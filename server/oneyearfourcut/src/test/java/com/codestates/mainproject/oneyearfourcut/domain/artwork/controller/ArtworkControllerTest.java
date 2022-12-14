package com.codestates.mainproject.oneyearfourcut.domain.artwork.controller;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkPatchDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.OneYearFourCutResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.PrincipalDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
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
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.util.List;

import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtworkController.class)
@MockBean({JpaMetamodelMappingContext.class, ClientRegistrationRepository.class, MemberRepository.class})
@AutoConfigureRestDocs
public class ArtworkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtworkService artworkService;

    @Autowired
    private Gson gson;


    @TestConfiguration
    static class testSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf().disable()
                    .build();
        }
    }

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
    @DisplayName("?????? ??????")
    public void postArtwork() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "image.png",
                "image/png",
                "<<image.png>>".getBytes());
        String title = "?????? ??? ??? - D25";
        String content = "?????????!";
        String imagePath = "/IMAGE_PATH";

        ArtworkResponseDto response = ArtworkResponseDto.builder()
                .artworkId(1L)
                .title(title)
                .content(content)
                .imagePath(imagePath)
                .build();

        given(artworkService.createArtwork(any(Long.class), eq(1L), any(ArtworkPostDto.class))).willReturn(response);

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.multipart("/galleries/{gallery-id}/artworks", 1L)
                        .file(image)
                        .param("title", title)
                        .param("content", content)
                        .header("Authorization", "Bearer (AccessToken)"));
        actions
                .andExpect(status().isCreated())
                .andDo(document("post-artwork",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT - Access Token")
                        ),
                        pathParameters(
                                parameterWithName("gallery-id").description("????????? ?????????")
                        ),
                        requestParameters(
                                parameterWithName("title").description("????????? ?????? ??????"),
                                parameterWithName("content").description("????????? ?????? ??????")
                        ),
                        requestParts(
                                partWithName("image").description("????????? ?????? ??????")
                        )));
    }

    @Test
    @DisplayName("?????? ??????")
    void patchArtworkTest() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "image.png",
                "image/png",
                "<<image.png>>".getBytes());
        String title = "????????? ??????";
        String content = "????????? ??????";

        Member writer = new Member(1L);
        Member reader = new Member(2L);
        Gallery gallery = new Gallery(1L);

        Artwork existingArtwork = new Artwork(1L);
        existingArtwork.setMember(writer);
        existingArtwork.setGallery(gallery);

        ArtworkResponseDto response = ArtworkResponseDto.builder()
                .artworkId(1L)
                .nickName("?????????")
                .title(title)
                .content(content)
                .imagePath("/S3_imagePath")
                .build();

        given(artworkService.updateArtwork(anyLong(), eq(1L), eq(1L), any(ArtworkPatchDto.class)))
                .willReturn(response);
        
        MockMultipartHttpServletRequestBuilder builder =
                RestDocumentationRequestBuilders.
                        multipart("/galleries/{gallery-id}/artworks/{artwork-id}", 1L, 1L);
        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        ResultActions actions =
                mockMvc.perform(
                        builder
                                .file(image)
                                .param("title", title)
                                .param("content", content)
                                .header("Authorization", "Bearer (AccessToken)")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                );
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artworkId").exists())
                .andExpect(jsonPath("$.memberId").exists())
                .andExpect(jsonPath("$.nickName").value(response.getNickName()))
                .andExpect(jsonPath("$.title").value(response.getTitle()))
                .andExpect(jsonPath("$.content").value(response.getContent()))
                .andExpect(jsonPath("$.imagePath").value(response.getImagePath()))
                .andExpect(jsonPath("$.likeCount").exists())
                .andExpect(jsonPath("$.liked").exists())
                .andExpect(jsonPath("$.commentCount").exists())
                .andDo(document("patch-artwork",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT - Access Token")
                        ),
                        pathParameters(
                                parameterWithName("gallery-id").description("????????? ?????????"),
                                parameterWithName("artwork-id").description("?????? ?????????")
                        )
                        , requestParameters(
                                parameterWithName("title").description("????????? ?????? ??????").optional(),
                                parameterWithName("content").description("????????? ?????? ??????").optional()
                        ),
                        requestParts(
                                partWithName("image").description("????????? ?????????").optional()
                        ),
                        responseFields(
                                fieldWithPath("artworkId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                fieldWithPath("imagePath").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                                fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("????????? ???"),
                                fieldWithPath("liked").type(JsonFieldType.BOOLEAN).description("????????? ?????? ????????? ????????? ????????? ??????"),
                                fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("?????? ???")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ??????")
    void getArtworks() throws Exception {

        List<ArtworkResponseDto> response = List.of(
                ArtworkResponseDto.builder().artworkId(1L).nickName("?????????1").title("???????????????.").content("???????????????.").imagePath("S3 ????????? url").likeCount(32).commentCount(3).build(),
                ArtworkResponseDto.builder().artworkId(2L).nickName("?????????2").title("???????????????.").content("???????????????.").imagePath("S3 ????????? url").likeCount(212).commentCount(2).build(),
                ArtworkResponseDto.builder().artworkId(3L).nickName("?????????3").title("???????????????.").content("???????????????.").imagePath("S3 ????????? url").likeCount(26).commentCount(5).build(),
                ArtworkResponseDto.builder().artworkId(4L).nickName("?????????4").title("???????????????.").content("???????????????.").imagePath("S3 ????????? url").likeCount(62).commentCount(6).build(),
                ArtworkResponseDto.builder().artworkId(5L).nickName("?????????5").title("???????????????.").content("???????????????.").imagePath("S3 ????????? url").likeCount(91).commentCount(1).build()
        );

        given(artworkService.findArtworkList(any(Long.class), eq(1L))).willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/galleries/{gallery-id}/artworks", 1L)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer (accessToken)")
                );
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0:1].artworkId").value(1))
                .andExpect(jsonPath("$.[1:2].artworkId").value(2))
                .andExpect(jsonPath("$.[2:3].artworkId").value(3))
                .andExpect(jsonPath("$.[3:4].artworkId").value(4))
                .andExpect(jsonPath("$.[-1:0].artworkId").value(5))

                .andDo(document("get-artworks",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        headerWithName("Authorization").description("JWT - Access Token").optional()
                                ),
                                pathParameters(
                                        parameterWithName("gallery-id").description("????????? ?????????")
                                ),
                                responseFields(
                                        fieldWithPath("[].artworkId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("[].nickName").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                        fieldWithPath("[].content").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                        fieldWithPath("[].imagePath").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                                        fieldWithPath("[].likeCount").type(JsonFieldType.NUMBER).description("????????? ???"),
                                        fieldWithPath("[].liked").type(JsonFieldType.BOOLEAN).description("????????? ?????? ????????? ????????? ????????? ??????"),
                                        fieldWithPath("[].commentCount").type(JsonFieldType.NUMBER).description("?????? ???")
                                )
                        )
                );
    }

    @Test
    @DisplayName("?????? ?????? ??????")
    void getArtwork() throws Exception {

        ArtworkResponseDto responseDto = ArtworkResponseDto.builder().artworkId(1L).nickName("?????????").title("???????????????.").content("???????????????.")
                .imagePath("S3 ????????? url").likeCount(22).commentCount(3).build();

        given(artworkService.findArtwork(any(Long.class), eq(1L), eq(1L))).willReturn(responseDto);

        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/galleries/{gallery-id}/artworks/{artwork-id}", 1L, 1L)
                                .header("Authorization", "Bearer (accessToken)")
                );
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artworkId").value(responseDto.getArtworkId()))
                .andExpect(jsonPath("$.memberId").value(responseDto.getMemberId()))
                .andExpect(jsonPath("$.nickName").value(responseDto.getNickName()))
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.imagePath").value(responseDto.getImagePath()))
                .andExpect(jsonPath("$.likeCount").value(responseDto.getLikeCount()))
                .andExpect(jsonPath("$.liked").value(responseDto.isLiked()))
                .andExpect(jsonPath("$.commentCount").value(responseDto.getCommentCount()))

                .andDo(document("get-artwork",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        headerWithName("Authorization").description("JWT - Access Token").optional()
                                ),
                                pathParameters(
                                        parameterWithName("gallery-id").description("????????? ?????????"),
                                        parameterWithName("artwork-id").description("?????? ?????????")
                                ),
                                responseFields(
                                        fieldWithPath("artworkId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                        fieldWithPath("imagePath").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                                        fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("????????? ???"),
                                        fieldWithPath("liked").type(JsonFieldType.BOOLEAN).description("????????? ?????? ????????? ????????? ????????? ??????"),
                                        fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("?????? ???")
                                )
                        )
                );
    }

    @Test
    @DisplayName("?????? ??? ??? ??????")
    void getOneYearFourCut() throws Exception {
        List<OneYearFourCutResponseDto> response = List.of(
                OneYearFourCutResponseDto.builder().artworkId(1L).imagePath("S3 ????????? url").likeCount(200).build(),
                OneYearFourCutResponseDto.builder().artworkId(2L).imagePath("S3 ????????? url").likeCount(150).build(),
                OneYearFourCutResponseDto.builder().artworkId(3L).imagePath("S3 ????????? url").likeCount(100).build(),
                OneYearFourCutResponseDto.builder().artworkId(4L).imagePath("S3 ????????? url").likeCount(50).build()
        );

        given(artworkService.findOneYearFourCut(eq(1L))).willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/galleries/{gallery-id}/artworks/like", 1L)
                );
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0:1].likeCount").value(response.get(0).getLikeCount()))
                .andExpect(jsonPath("$.[1:2].likeCount").value(response.get(1).getLikeCount()))
                .andExpect(jsonPath("$.[2:3].likeCount").value(response.get(2).getLikeCount()))
                .andExpect(jsonPath("$.[-1:0].likeCount").value(response.get(3).getLikeCount()))
                .andDo(document("get-oneYearFourCut",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        parameterWithName("gallery-id").description("????????? ?????????")
                                ),
                                responseFields(
                                        fieldWithPath("[].artworkId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("[].imagePath").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                                        fieldWithPath("[].likeCount").type(JsonFieldType.NUMBER).description("????????? ???")
                                )
                        )
                );
    }

    @Test
    void deleteArtworkTest() throws Exception {

        willDoNothing().given(artworkService).deleteArtwork(any(Long.class), eq(1L), eq(1L));

        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/galleries/{gallery-id}/artworks/{artwork-id}", 1L, 1L)
                                .header("Authorization", "Bearer (AccessToken)")
                );
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-artwork",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT - Access Token")
                        ),
                        pathParameters(
                                parameterWithName("gallery-id").description("????????? ?????????"),
                                parameterWithName("artwork-id").description("?????? ?????????")
                        )
                ));
    }
}