package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.CommentRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.ReplyRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.ReplyService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Role;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.oneyearfourcut.global.page.ReplyListResponseDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.VALID;
import static com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus.ACTIVE;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
    @Autowired
    private ReplyRepository replyRepository;
    @MockBean
    private ReplyService replyService;
    @Autowired
    private Gson gson;

    @Test
    void testPostReply() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("test1@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());
        String jwt = jwtTokenizer.testJwtGenerator(member);
        Gallery gallery = galleryRepository.save(new Gallery(1L));

        CommentRequestDto requestDto = CommentRequestDto.builder()
                .content("답글입니다")
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
                        .post("/galleries/comments/{commentId}/replies",
                                comment.getCommentId())
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
                .andExpect(jsonPath("$.replyList.memberId").value(member.getMemberId()))
                .andExpect(jsonPath("$.replyList.nickname").value("test1"))
                .andExpect(jsonPath("$.replyList.content").value("답글입니다"))

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
    void testGetReply() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("test1@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());
        Gallery gallery = galleryRepository.save(new Gallery(1L));

        Comment comment = commentRepository.save(Comment.builder()
                .commentId(1L)
                .content("댓글")
                .member(member)
                .gallery(gallery)
                .artworkId(1L)
                .commentStatus(CommentStatus.VALID)
                .build());

        replyRepository.save(Reply.builder()
                .replyId(4L)
                .content("답글")
                .member(member)
                .comment(comment)
                .replyStatus(CommentStatus.VALID)
                .build());

        given(this.replyService.findReplyList(
                Mockito.any( comment.getCommentId().getClass() )))
                .willReturn(replyRepository.findAllByReplyStatusAndComment_CommentIdOrderByReplyIdDesc(VALID, 1L));

        List<Reply> replyList = replyService.findReplyList(1L);
        List<ReplyResDto> result = ReplyResDto.toReplyResponseDtoList(replyList);

        given(this.replyService.getReplyList(
                Mockito.any( comment.getCommentId().getClass())))
                .willReturn(new ReplyListResponseDto<>(1L,result));

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/galleries/comments/{commentId}/replies",
                                comment.getCommentId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        /*.with(csrf())*/
        );
        replyService.getReplyList(comment.getCommentId());

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value( comment.getCommentId()))
                .andExpect(jsonPath("$.replyList[0].replyId").value(4L))
                .andExpect(jsonPath("$.replyList[0].memberId").value(member.getMemberId()))
                .andExpect(jsonPath("$.replyList[0].nickname").value("test1"))
                .andExpect(jsonPath("$.replyList[0].content").value("답글"))

                .andDo(document(
                                "getReply",
                                getRequestPreProcessor(),
                                getResponsePreProcessor()
                                ,pathParameters(
                                        parameterWithName("commentId").description("댓글 Id"))
                                , responseFields(
                                        List.of(
                                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                                fieldWithPath("replyList[].createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                                fieldWithPath("replyList[].modifiedAt").type(JsonFieldType.STRING).description("생성일자"),
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
    void testPatchReply() throws Exception{
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("test1@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());
        String jwt = jwtTokenizer.testJwtGenerator(member);

        Gallery gallery = galleryRepository.save(Gallery.builder()
                .title("나의 전시관")
                .content("안녕하세요")
                .member(member)
                .status(GalleryStatus.OPEN)
                .build());

        Comment comment = commentRepository.save(Comment.builder()
                .commentId(4L)
                .content("댓글")
                .member(member)
                .gallery(gallery)
                .artworkId(null)
                .commentStatus(CommentStatus.VALID)
                .build());

        replyRepository.save(Reply.builder()
                .replyId(1L)
                .content("답글1")
                .member(member)
                .comment(comment)
                .replyStatus(CommentStatus.VALID)
                .build());

        replyRepository.save(Reply.builder()
                .replyId(2L)
                .content("답글2")
                .member(member)
                .comment(comment)
                .replyStatus(CommentStatus.VALID)
                .build());

        CommentRequestDto requestDto = CommentRequestDto.builder()
                .content("수정대댓글입니다.")
                .build();
        String gsonContent = gson.toJson(requestDto);

        Long replyId = 1L;

        given(this.replyService.findReplyList(
                Mockito.any( comment.getCommentId().getClass() )))
                .willReturn(replyRepository.findAllByReplyStatusAndComment_CommentIdOrderByReplyIdDesc(VALID, 3L));

        List<Reply> replyList = replyService.findReplyList(3L);
        List<ReplyResDto> result = ReplyResDto.toReplyResponseDtoList(replyList);

        ReplyResDto responseDto = ReplyResDto.builder()
                .createdAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .modifiedAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .replyId(replyId)
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .content(requestDto.getContent())
                .build();

        given(this.replyService.modifyReply(
                Mockito.any( comment.getCommentId().getClass()),
                eq(replyId),
                Mockito.any( requestDto.getClass() ),
                Mockito.any( member.getMemberId().getClass() )))
                .willReturn(new ReplyListResponseDto<>( gallery.getGalleryId() , responseDto));

        replyService.modifyReply(4L,1L, requestDto ,member.getMemberId());

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/galleries/comments/{commentId}/replies/{replyId}",
                               4L, 1L)
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonContent)
                /*.with(csrf())*/
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value( comment.getCommentId()))
                .andExpect(jsonPath("$.replyList.memberId").value(member.getMemberId()))
                .andExpect(jsonPath("$.replyList.nickname").value("test1"))
                .andExpect(jsonPath("$.replyList.content").value("수정대댓글입니다."))

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
    void testDeleteReply() throws Exception{
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("test1@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());
        String jwt = jwtTokenizer.testJwtGenerator(member);

        Gallery gallery = galleryRepository.save(Gallery.builder()
                .title("나의 전시관")
                .content("안녕하세요")
                .member(member)
                .status(GalleryStatus.OPEN)
                .build());

        Comment comment = commentRepository.save(Comment.builder()
                .commentId(3L)
                .content("댓글")
                .member(member)
                .gallery(gallery)
                .artworkId(null)
                .commentStatus(CommentStatus.VALID)
                .build());

        replyRepository.save(Reply.builder()
                .replyId(3L)
                .content("답글")
                .member(member)
                .comment(comment)
                .replyStatus(CommentStatus.VALID)
                .build());

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/galleries/comments/{commentId}/replies/{replyId}",
                                3L, 3L)
                        .header("Authorization", jwt)
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
