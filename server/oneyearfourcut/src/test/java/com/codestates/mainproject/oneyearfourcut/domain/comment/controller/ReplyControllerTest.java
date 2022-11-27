package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentArtworkResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.CommentRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.ReplyService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Role;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.oneyearfourcut.global.page.CommentArtworkHeadDto;
import com.codestates.mainproject.oneyearfourcut.global.page.ReplyListResponseDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus.ACTIVE;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WithMockUser(username = "test@gmail.com", password = "0000")
class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private CommentRepository commentRepository;

    @MockBean
    private ReplyService replyService;
    @Autowired
    private Gson gson;

    @Test
    void testPostReply() throws Exception {
        //given
        //test전 member 등록  //해당 member jwt 생성
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("test1@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());
        String jwt = jwtTokenizer.testJwtGenerator(member);
        Gallery gallery = galleryRepository.save(new Gallery(1L));


        //test하려는 gallery request
        CommentRequestDto requestDto = CommentRequestDto.builder()
                .content("대댓글입니다")
                .build();
        String gsonContent = gson.toJson(requestDto);

        ReplyResDto responseDto = ReplyResDto.builder()
                .createdAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .modifiedAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .replyId(1L)
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .content(requestDto.getContent())
                .build();

        Comment comment = commentRepository.save(Comment.builder()
                .commentId(1L)
                .content("댓글")
                .member(member)
                .gallery(gallery)
                .artworkId(1L)
                .commentStatus(CommentStatus.VALID)
                .build());


        given(this.replyService.createReply(
                Mockito.any(requestDto.getClass()),
                Mockito.any( comment.getCommentId().getClass()),
                Mockito.any( member.getMemberId().getClass() )))
                .willReturn(new ReplyListResponseDto<>(1L,responseDto));

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/galleries/comments/{replyId}/replies",
                                comment.getCommentId(), 1L)
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonContent)
                /*.with(csrf())*/
        );
        replyService.createReply(requestDto, comment.getCommentId(),member.getMemberId());

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId").value( comment.getCommentId()))
                .andExpect(jsonPath("$.replyList.createdAt").value("2022-11-25T11:09:24.94"))
                .andExpect(jsonPath("$.replyList.modifiedAt").value("2022-11-25T11:09:24.94"))
                .andExpect(jsonPath("$.replyList.replyId").value(1L))
                .andExpect(jsonPath("$.replyList.memberId").value(9L))
                .andExpect(jsonPath("$.replyList.nickname").value("test1"))
                .andExpect(jsonPath("$.replyList.content").value("대댓글입니다"))

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
                                                fieldWithPath("content").type(JsonFieldType.STRING).description("대댓글 내용")
                                        )
                                )
                                , responseFields(
                                        List.of(
                                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                                fieldWithPath("replyList.createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("replyList.modifiedAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("replyList.replyId").type(JsonFieldType.NUMBER).description("대댓글 ID"),
                                                fieldWithPath("replyList.memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                                fieldWithPath("replyList.nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("replyList.content").type(JsonFieldType.STRING).description("대댓글 내용")

                                        )
                                )
                        )
                );

    }

}
