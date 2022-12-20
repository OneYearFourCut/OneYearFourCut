package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentArtworkResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentGalleryResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.PrincipalDto;
import com.codestates.mainproject.oneyearfourcut.global.page.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@MockBean({JpaMetamodelMappingContext.class, ClientRegistrationRepository.class, MemberRepository.class})
@AutoConfigureRestDocs
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private Gson gson;

    LocalDateTime time = LocalDateTime.of(2022, 12, 25, 12, 25, 25);

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
    void postCommentOnGalleryTest() throws Exception {
        Gallery gallery = new Gallery(1L);

        CommentRequestDto request = CommentRequestDto
                .builder()
                .content("텍스트")
                .build();

        CommentGalleryResDto responseDto = CommentGalleryResDto.builder()
                .commentId(1L)
                .createdAt(time)
                .modifiedAt(time)
                .memberId(1L)
                .nickname("사용자")
                .content(request.getContent())
                .build();

        CommentGalleryHeadDto<Object> response = new CommentGalleryHeadDto<>(gallery.getGalleryId(), responseDto);

        given(commentService.createCommentOnGallery(any(CommentRequestDto.class), eq(gallery.getGalleryId()), anyLong()))
                .willReturn(response);

        String gsonContent = gson.toJson(request);

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/galleries/{galleryId}/comments", gallery.getGalleryId())
                        .header("Authorization", "Bearer (AccessToken)")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonContent)
        );

        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentList.commentId").value(responseDto.getCommentId()))
                .andExpect(jsonPath("$.commentList.content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.commentList.createdAt").value(String.valueOf(responseDto.getCreatedAt())))
                .andExpect(jsonPath("$.commentList.modifiedAt").value(String.valueOf(responseDto.getModifiedAt())))
                .andExpect(jsonPath("$.commentList.memberId").value(responseDto.getMemberId()))
                .andExpect(jsonPath("$.commentList.nickname").value(responseDto.getNickname()))
                .andExpect(jsonPath("$.galleryId").value(gallery.getGalleryId()))
                .andExpect(jsonPath("$.commentList.artworkId").doesNotExist())
                .andDo(document(
                                "postCommentOnGallery",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        List.of(
                                                headerWithName("Authorization").description("JWT - Access Token")
                                        )
                                ),
                                pathParameters(
                                        parameterWithName("galleryId").description("갤러리 Id")),
                                requestFields(
                                        List.of(
                                                fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
                                        )
                                )
                                , responseFields(
                                        List.of(
                                                fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("전시관 ID"),
                                                fieldWithPath("commentList.createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("commentList.modifiedAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("commentList.commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                                fieldWithPath("commentList.memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                                fieldWithPath("commentList.nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("commentList.content").type(JsonFieldType.STRING).description("댓글 내용"),
                                                fieldWithPath("commentList.artworkId").type(JsonFieldType.NUMBER).description("작품 ID").optional(),
                                                fieldWithPath("commentList.imagePath").type(JsonFieldType.NULL).description("작품 이미지 url")
                                        )
                                )
                        )
                );
    }

    @Test
    void postCommentOnArtworkTest() throws Exception {
        Gallery gallery = new Gallery(1L);
        Artwork artwork = new Artwork(1L);
        CommentRequestDto request = CommentRequestDto.builder()
                .content("텍스트")
                .build();

        CommentArtworkResDto responseDto = CommentArtworkResDto.builder()
                .commentId(1L)
                .createdAt(time)
                .modifiedAt(time)
                .memberId(1L)
                .nickname("사용자")
                .content("텍스트")
                .build();
        CommentArtworkHeadDto<Object> response = new CommentArtworkHeadDto<>(gallery.getGalleryId(), artwork.getArtworkId(), responseDto);

        given(commentService.createCommentOnArtwork(any(CommentRequestDto.class), eq(gallery.getGalleryId()), anyLong(), eq(artwork.getArtworkId())))
                .willReturn(response);

        String gsonContent = gson.toJson(request);

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/galleries/{galleryId}/artworks/{artworkId}/comments",
                                gallery.getGalleryId(), artwork.getArtworkId())
                        .header("Authorization", "Bearer (AccessToken)")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonContent)
        );

        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentList.commentId").value(responseDto.getCommentId()))
                .andExpect(jsonPath("$.commentList.content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.commentList.createdAt").value(String.valueOf(responseDto.getCreatedAt())))
                .andExpect(jsonPath("$.commentList.modifiedAt").value(String.valueOf(responseDto.getModifiedAt())))
                .andExpect(jsonPath("$.commentList.memberId").value(responseDto.getMemberId()))
                .andExpect(jsonPath("$.commentList.nickname").value(responseDto.getNickname()))
                .andExpect(jsonPath("$.galleryId").value(gallery.getGalleryId()))
                .andExpect(jsonPath("$.artworkId").value(artwork.getArtworkId()))

                .andDo(document(
                                "postCommentOnArtwork",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        List.of(
                                                headerWithName("Authorization").description("JWT - Access Token")
                                        )
                                )
                                , pathParameters(
                                        parameterWithName("galleryId").description("갤러리 Id"),
                                        parameterWithName("artworkId").description("작품 Id")),

                                requestFields(
                                        List.of(
                                                fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
                                        )
                                )
                                , responseFields(
                                        List.of(
                                                fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("전시관 ID"),
                                                fieldWithPath("artworkId").type(JsonFieldType.NUMBER).description("작품 ID"),
                                                fieldWithPath("commentList.createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("commentList.modifiedAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("commentList.commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                                fieldWithPath("commentList.memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                                fieldWithPath("commentList.nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("commentList.content").type(JsonFieldType.STRING).description("댓글 내용")
                                        )
                                )
                        )
                );

    }

    @Test
    void getGalleryCommentTest() throws Exception {
        Gallery gallery = new Gallery(1L);
        int page = 1;
        int size = 10;
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, 4, 1);
        List<CommentGalleryResDto> collect = List.of(
                CommentGalleryResDto.builder()
                        .commentId(1L)
                        .createdAt(time)
                        .modifiedAt(time)
                        .memberId(1L)
                        .nickname("사용자1")
                        .content("댓글1")
                        .build(),
                CommentGalleryResDto.builder()
                        .commentId(2L)
                        .createdAt(time)
                        .modifiedAt(time)
                        .memberId(2L)
                        .nickname("사용자2")
                        .content("댓글2")
                        .artworkId(1L)
                        .imagePath("artwork1.jpg").build(),
                CommentGalleryResDto.builder()
                        .commentId(3L)
                        .createdAt(time)
                        .modifiedAt(time)
                        .memberId(3L)
                        .nickname("사용자3")
                        .content("댓글3")
                        .build(),
                CommentGalleryResDto.builder()
                        .commentId(4L)
                        .createdAt(time)
                        .modifiedAt(time)
                        .memberId(4L)
                        .nickname("사용자4")
                        .content("댓글4")
                        .build());
        CommentGalleryPageResponseDto<Object> response = new CommentGalleryPageResponseDto<>(gallery.getGalleryId(), collect, pageInfo);
        given(commentService.getGalleryCommentPage(eq(gallery.getGalleryId()), eq(page), eq(size))).willReturn(response);

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/galleries/{galleryId}/comments",
                                gallery.getGalleryId())
                        .param("page", String.valueOf(1))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        commentService.getGalleryCommentPage(gallery.getGalleryId(), 1, 10);

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.galleryId").value(gallery.getGalleryId()))
                .andExpect(jsonPath("$.commentList[0].createdAt").value(String.valueOf(collect.get(0).getCreatedAt())))
                .andExpect(jsonPath("$.commentList[0].modifiedAt").value(String.valueOf(collect.get(0).getModifiedAt())))
                .andExpect(jsonPath("$.commentList[0].commentId").value(collect.get(0).getCommentId()))
                .andExpect(jsonPath("$.commentList[0].memberId").value(collect.get(0).getMemberId()))
                .andExpect(jsonPath("$.commentList[0].nickname").value(collect.get(0).getNickname()))
                .andExpect(jsonPath("$.commentList[0].content").value(collect.get(0).getContent()))
                .andExpect(jsonPath("$.commentList[0].artworkId").doesNotExist())
                .andExpect(jsonPath("$.commentList[0].imagePath").doesNotExist())

                .andExpect(jsonPath("$.commentList[1].createdAt").value(String.valueOf(collect.get(1).getCreatedAt())))
                .andExpect(jsonPath("$.commentList[1].modifiedAt").value(String.valueOf(collect.get(1).getModifiedAt())))
                .andExpect(jsonPath("$.commentList[1].commentId").value(collect.get(1).getCommentId()))
                .andExpect(jsonPath("$.commentList[1].memberId").value(collect.get(1).getMemberId()))
                .andExpect(jsonPath("$.commentList[1].nickname").value(collect.get(1).getNickname()))
                .andExpect(jsonPath("$.commentList[1].content").value(collect.get(1).getContent()))
                .andExpect(jsonPath("$.commentList[1].artworkId").value(collect.get(1).getArtworkId()))
                .andExpect(jsonPath("$.commentList[1].imagePath").value(collect.get(1).getImagePath()))

                .andExpect(jsonPath("$.pageInfo.page").value(pageInfo.getPage()))
                .andExpect(jsonPath("$.pageInfo.size").value(pageInfo.getSize()))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(pageInfo.getTotalElements()))
                .andExpect(jsonPath("$.pageInfo.totalPages").value(pageInfo.getTotalPages()))

                .andDo(document(
                                "getCommentOnGallery",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestParameters(parameterWithName("page").description("페이지 수")
                                )
                                , pathParameters(
                                        parameterWithName("galleryId").description("갤러리 Id"))
                                , responseFields(
                                        List.of(
                                                fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("전시관 ID"),
                                                fieldWithPath("commentList[].createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("commentList[].modifiedAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("commentList[].commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                                fieldWithPath("commentList[].memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                                fieldWithPath("commentList[].nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("commentList[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                                                fieldWithPath("commentList[].artworkId").type(JsonFieldType.NUMBER).description("작품 ID").optional(),
                                                fieldWithPath("commentList[].imagePath").type(JsonFieldType.STRING).description("작품 이미지 URL (없으면 null)").optional(),

                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현 페이지 위치"),
                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 내용 크기"),
                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 댓글 수"),
                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
                                        )
                                )
                        )
                );
    }

    @Test
    void getArtworkCommentTest() throws Exception {
        Gallery gallery = new Gallery(1L);
        Artwork artwork = new Artwork(1L);
        int page = 1;
        int size = 10;
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, 4, 1);
        List<CommentArtworkResDto> collect = List.of(
                CommentArtworkResDto.builder()
                        .commentId(1L)
                        .createdAt(time)
                        .modifiedAt(time)
                        .memberId(1L)
                        .nickname("사용자1")
                        .content("댓글1")
                        .build(),
                CommentArtworkResDto.builder()
                        .commentId(2L)
                        .createdAt(time)
                        .modifiedAt(time)
                        .memberId(2L)
                        .nickname("사용자2")
                        .content("댓글2")
                        .build(),
                CommentArtworkResDto.builder()
                        .commentId(3L)
                        .createdAt(time)
                        .modifiedAt(time)
                        .memberId(3L)
                        .nickname("사용자3")
                        .content("댓글3")
                        .build(),
                CommentArtworkResDto.builder()
                        .commentId(4L)
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .memberId(4L)
                        .nickname("사용자4")
                        .content("댓글4")
                        .build());
        CommentArtworkPageResponseDto<Object> response = new CommentArtworkPageResponseDto<>(gallery.getGalleryId(), artwork.getArtworkId(), collect, pageInfo);
        given(commentService.getArtworkCommentPage(eq(gallery.getGalleryId()), eq(artwork.getArtworkId()), eq(page), eq(size))).willReturn(response);

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/galleries/{galleryId}/artworks/{artworkId}/comments",
                                gallery.getGalleryId(), artwork.getArtworkId())
                        .param("page", String.valueOf(1))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                /*.with(csrf())*/
        );
        commentService.getArtworkCommentPage(gallery.getGalleryId(), 1L, 1, 10);

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.galleryId").value(gallery.getGalleryId()))
                .andExpect(jsonPath("$.artworkId").value(artwork.getArtworkId()))
                .andExpect(jsonPath("$.commentList[0].commentId").value(collect.get(0).getCommentId()))
                .andExpect(jsonPath("$.commentList[0].memberId").value(collect.get(0).getMemberId()))
                .andExpect(jsonPath("$.commentList[0].nickname").value(collect.get(0).getNickname()))
                .andExpect(jsonPath("$.commentList[0].content").value(collect.get(0).getContent()))

                .andExpect(jsonPath("$.pageInfo.page").value(pageInfo.getPage()))
                .andExpect(jsonPath("$.pageInfo.size").value(pageInfo.getSize()))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(pageInfo.getTotalElements()))
                .andExpect(jsonPath("$.pageInfo.totalPages").value(pageInfo.getTotalPages()))


                .andDo(document(
                                "getCommentOnArtwork",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestParameters(parameterWithName("page").description("페이지 수")
                                )
                                , pathParameters(
                                        parameterWithName("galleryId").description("갤러리 Id"),
                                        parameterWithName("artworkId").description("작품 Id"))
                                , responseFields(
                                        List.of(
                                                fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("전시관 ID"),
                                                fieldWithPath("artworkId").type(JsonFieldType.NUMBER).description("작품 ID"),
                                                fieldWithPath("commentList[].createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("commentList[].modifiedAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("commentList[].commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                                fieldWithPath("commentList[].memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                                fieldWithPath("commentList[].nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("commentList[].content").type(JsonFieldType.STRING).description("댓글 내용"),

                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현 페이지 위치"),
                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 내용 크기"),
                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 댓글 수"),
                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
                                        )
                                )
                        )
                );
    }

    @Test
    void patchCommentTest() throws Exception {
        Gallery gallery = new Gallery(1L);
        CommentRequestDto requestDto = CommentRequestDto.builder().content("수정된 댓글").build();

        CommentGalleryResDto responseDto = CommentGalleryResDto.builder()
                .commentId(1L)
                .createdAt(time)
                .modifiedAt(time)
                .memberId(1L)
                .nickname("사용자")
                .content(requestDto.getContent())
                .build();

        CommentGalleryHeadDto<Object> response = new CommentGalleryHeadDto<>(gallery.getGalleryId(), responseDto);
        given(commentService.modifyComment(eq(gallery.getGalleryId()), eq(responseDto.getCommentId()), any(CommentRequestDto.class), anyLong()))
                .willReturn(response);

        String gsonContent = gson.toJson(requestDto);

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/galleries/{galleryId}/comments/{commentId}",
                                gallery.getGalleryId(), responseDto.getCommentId())
                        .header("Authorization", "Bearer (AccessToken)")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonContent)
        );
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.galleryId").value(gallery.getGalleryId()))
                .andExpect(jsonPath("$.commentList.commentId").value(responseDto.getCommentId()))
                .andExpect(jsonPath("$.commentList.memberId").value(responseDto.getMemberId()))
                .andExpect(jsonPath("$.commentList.nickname").value(responseDto.getNickname()))
                .andExpect(jsonPath("$.commentList.content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.commentList.artworkId").doesNotExist())

                .andDo(document(
                                "patchComment",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        List.of(
                                                headerWithName("Authorization").description("JWT - Access Token")
                                        )
                                ),
                                pathParameters(
                                        parameterWithName("galleryId").description("갤러리 Id"),
                                        parameterWithName("commentId").description("댓글 Id")),
                                requestFields(
                                        List.of(
                                                fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
                                        )
                                )
                                , responseFields(
                                        List.of(
                                                fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("전시관 ID"),
                                                fieldWithPath("commentList.createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("commentList.modifiedAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("commentList.commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                                fieldWithPath("commentList.memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                                fieldWithPath("commentList.nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("commentList.content").type(JsonFieldType.STRING).description("댓글 내용"),
                                                fieldWithPath("commentList.artworkId").type(JsonFieldType.NUMBER).description("작품 ID").optional(),
                                                fieldWithPath("commentList.imagePath").type(JsonFieldType.NULL).description("작품 이미지 url").optional()
                                        )
                                )
                        )
                );
    }

    @Test
    void deleteCommentTest() throws Exception {
        Member member = new Member(1L);
        Gallery gallery = new Gallery(1L);
        Comment comment = new Comment(1L);

        willDoNothing().given(commentService).deleteComment(eq(gallery.getGalleryId()), eq(comment.getCommentId()), eq(member.getMemberId()));

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/galleries/{galleryId}/comments/{commentId}",
                                gallery.getGalleryId(), comment.getCommentId())
                        .header("Authorization", "Bearer (AccessToken)")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        actions.andExpect(status().isNoContent())
                .andDo(document(
                                "deleteComment",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        List.of(
                                                headerWithName("Authorization").description("JWT - Access Token")
                                        )
                                ),
                                pathParameters(
                                        parameterWithName("galleryId").description("갤러리 Id"),
                                        parameterWithName("commentId").description("댓글 Id"))
                        )

                );


    }
}
