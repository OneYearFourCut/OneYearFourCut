package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.ReplyService;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.PrincipalDto;
import com.codestates.mainproject.oneyearfourcut.global.page.ReplyListResponseDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplyController.class)
@MockBean({JpaMetamodelMappingContext.class, ClientRegistrationRepository.class, MemberRepository.class})
@AutoConfigureRestDocs
public class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReplyService replyService;

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
    void postReplyTest() throws Exception {
        Long commentId = 1L;
        Long memberId = 1L;

        CommentRequestDto requestDto = CommentRequestDto.builder().content("대댓글").build();

        ReplyResDto responseDto = ReplyResDto.builder()
                .replyId(1L)
                .memberId(memberId)
                .nickname("작성자")
                .content(requestDto.getContent())
                .createdAt(time)
                .modifiedAt(time).build();
        ReplyListResponseDto<Object> response = new ReplyListResponseDto<>(commentId, responseDto);
        System.out.println(response.getCommentId());
        given(replyService.createReply(any(CommentRequestDto.class), eq(commentId), eq(memberId))).willReturn(response);

        String gsonContent = gson.toJson(requestDto);
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/galleries/comments/{commentId}/replies",
                                commentId)
                        .header("Authorization", "Bearer (AccessToken)")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonContent)
        );
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId").value(response.getCommentId()))
                .andExpect(jsonPath("$.replyList.createdAt").value(responseDto.getCreatedAt().toString()))
                .andExpect(jsonPath("$.replyList.modifiedAt").value(responseDto.getModifiedAt().toString()))
                .andExpect(jsonPath("$.replyList.replyId").value(responseDto.getReplyId()))
                .andExpect(jsonPath("$.replyList.memberId").value(responseDto.getMemberId()))
                .andExpect(jsonPath("$.replyList.nickname").value(responseDto.getNickname()))
                .andExpect(jsonPath("$.replyList.content").value(responseDto.getContent()))

                .andDo(document(
                                "postReply",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        List.of(
                                                headerWithName("Authorization").description("JWT - Access Token")
                                        )
                                ),
                                requestFields(
                                        List.of(
                                                fieldWithPath("content").type(JsonFieldType.STRING).description("답글 내용")
                                        )
                                )
                                ,pathParameters(
                                        parameterWithName("commentId").description("댓글 Id"))
                                , responseFields(
                                        List.of(
                                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                                fieldWithPath("replyList.createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("replyList.modifiedAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("replyList.replyId").type(JsonFieldType.NUMBER).description("답글 ID"),
                                                fieldWithPath("replyList.memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                                fieldWithPath("replyList.nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("replyList.content").type(JsonFieldType.STRING).description("답글 내용")

                                        )
                                )
                        )
                );
    }

    @Test
    void getReplyTest() throws Exception {
        Long commentId = 1L;

        List<ReplyResDto> responseDtoList = List.of(
                ReplyResDto.builder()
                        .replyId(1L)
                        .memberId(1L)
                        .nickname("작성자1")
                        .content("대댓글1")
                        .createdAt(time)
                        .modifiedAt(time).build(),
                ReplyResDto.builder()
                        .replyId(2L)
                        .memberId(2L)
                        .nickname("작성자2")
                        .content("대댓글2")
                        .createdAt(time)
                        .modifiedAt(time).build(),
                ReplyResDto.builder()
                        .replyId(3L)
                        .memberId(3L)
                        .nickname("작성자3")
                        .content("대댓글3")
                        .createdAt(time)
                        .modifiedAt(time).build());

        ReplyListResponseDto<Object> response = new ReplyListResponseDto<>(commentId, responseDtoList);

        given(replyService.getReplyList(eq(commentId))).willReturn(response);

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/galleries/comments/{commentId}/replies",
                                commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(response.getCommentId()))
                .andExpect(jsonPath("$.replyList[0].replyId").value(responseDtoList.get(0).getReplyId()))
                .andExpect(jsonPath("$.replyList[0].memberId").value(responseDtoList.get(0).getMemberId()))
                .andExpect(jsonPath("$.replyList[0].nickname").value(responseDtoList.get(0).getNickname()))
                .andExpect(jsonPath("$.replyList[0].content").value(responseDtoList.get(0).getContent()))

                .andDo(document(
                                "getReply",
                                getRequestPreProcessor(),
                                getResponsePreProcessor()
                                ,pathParameters(
                                        parameterWithName("commentId").description("댓글 Id"))
                                , responseFields(
                                        List.of(
                                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                                fieldWithPath("replyList[].createdAt").type(JsonFieldType.STRING).description("생성일자").optional(),
                                                fieldWithPath("replyList[].modifiedAt").type(JsonFieldType.STRING).description("생성일자").optional(),
                                                fieldWithPath("replyList[].replyId").type(JsonFieldType.NUMBER).description("답글 ID"),
                                                fieldWithPath("replyList[].memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                                fieldWithPath("replyList[].nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("replyList[].content").type(JsonFieldType.STRING).description("답글 내용")

                                        )
                                )
                        )
                );

    }

    @Test
    void patchReplyTest() throws Exception {
        Long commentId = 1L;
        Long memberId = 1L;
        Long replyId = 1L;
        CommentRequestDto requestDto = CommentRequestDto.builder().content("수정대댓글").build();

        ReplyResDto responseDto = ReplyResDto.builder()
                .replyId(1L)
                .memberId(memberId)
                .nickname("작성자")
                .content(requestDto.getContent())
                .createdAt(time)
                .modifiedAt(time).build();
        ReplyListResponseDto<Object> response = new ReplyListResponseDto<>(commentId, responseDto);

        given(replyService.modifyReply(eq(commentId), eq(replyId), any(CommentRequestDto.class), eq(memberId))).willReturn(response);

        String gsonContent = gson.toJson(requestDto);

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/galleries/comments/{commentId}/replies/{replyId}",
                                commentId, replyId)
                        .header("Authorization", "Bearer (AccessToken)")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonContent)
        );

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(response.getCommentId()))
                .andExpect(jsonPath("$.replyList.memberId").value(responseDto.getMemberId()))
                .andExpect(jsonPath("$.replyList.nickname").value(responseDto.getNickname()))
                .andExpect(jsonPath("$.replyList.content").value(responseDto.getContent()))

                .andDo(document(
                                "patchReply",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        List.of(
                                                headerWithName("Authorization").description("JWT - Access Token")
                                        )
                                ),
                                pathParameters(
                                        parameterWithName("commentId").description("댓글 Id"),
                                        parameterWithName("replyId").description("답글 Id"))
                                ,requestFields(
                                        List.of(
                                                fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
                                        )
                                )
                                , responseFields(
                                        List.of(
                                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("전시관 ID"),
                                                fieldWithPath("replyList.createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("replyList.modifiedAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("replyList.replyId").type(JsonFieldType.NUMBER).description("답글 ID"),
                                                fieldWithPath("replyList.memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                                fieldWithPath("replyList.nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("replyList.content").type(JsonFieldType.STRING).description("댓글 내용")
                                        )
                                )
                        )
                );
    }

    @Test
    void deleteReplyTest() throws Exception {
        Long commentId = 1L;
        Long memberId = 1L;
        Long replyId = 1L;

        willDoNothing().given(replyService).deleteReply(eq(commentId), eq(replyId), eq(memberId));

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/galleries/comments/{commentId}/replies/{replyId}",
                                commentId, replyId)
                        .header("Authorization", "Bearer (AccessToken)")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                /*.with(csrf())*/
        );

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document(
                                "deleteReply",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        List.of(
                                                headerWithName("Authorization").description("JWT - Access Token")
                                        )
                                ),
                                pathParameters(
                                        parameterWithName("commentId").description("댓글 Id"),
                                        parameterWithName("replyId").description("답글 Id"))
                        )
                );
    }
}
