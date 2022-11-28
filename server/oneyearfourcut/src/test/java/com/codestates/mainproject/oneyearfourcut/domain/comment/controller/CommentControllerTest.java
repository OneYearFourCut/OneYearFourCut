package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentArtworkResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentGalleryResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.CommentRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Role;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.oneyearfourcut.global.page.CommentArtworkHeadDto;
import com.codestates.mainproject.oneyearfourcut.global.page.CommentGalleryHeadDto;
import com.codestates.mainproject.oneyearfourcut.global.page.CommentGalleryPageResponseDto;
import com.codestates.mainproject.oneyearfourcut.global.page.PageInfo;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.VALID;
import static com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus.ACTIVE;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WithMockUser(username = "test@gmail.com", password = "0000")
class CommentControllerTest {

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
    private CommentService commentService;
    @Autowired
    private Gson gson;


    @Test
    void testPostCommentOnGallery() throws Exception{
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
                .content("댓글입니다")
                .build();
        String gsonContent = gson.toJson(requestDto);

        CommentGalleryResDto responseDto = CommentGalleryResDto.builder()
                .createdAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .modifiedAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .commentId(1L)
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .content(requestDto.getContent())
                .artworkId(null)
                .build();

        given(this.commentService.createCommentOnGallery(Mockito.any(requestDto.getClass()),
                Mockito.any( member.getMemberId().getClass() ),
                Mockito.any( gallery.getGalleryId().getClass() ) ) )
                .willReturn(new CommentGalleryHeadDto<>(1L,responseDto));

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/galleries/{galleryId}/comments", gallery.getGalleryId())
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonContent)
                        /*.with(csrf())*/
        );
        commentService.createCommentOnGallery(requestDto, member.getMemberId(), gallery.getGalleryId());

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.galleryId").value( gallery.getGalleryId()))
                .andExpect(jsonPath("$.commentList.createdAt").value("2022-11-25T11:09:24.94"))
                .andExpect(jsonPath("$.commentList.modifiedAt").value("2022-11-25T11:09:24.94"))
                .andExpect(jsonPath("$.commentList.commentId").value(1L))
                .andExpect(jsonPath("$.commentList.memberId").value(member.getMemberId()))
                .andExpect(jsonPath("$.commentList.nickname").value("test1"))
                .andExpect(jsonPath("$.commentList.content").value("댓글입니다"))
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
                                        fieldWithPath("commentList.artworkId").type(JsonFieldType.NULL).description("작품 ID")
                                )
                        )
                )
                );


    }

    @Test
    void testPostCommentOnArtwork() throws Exception {
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
                .content("댓글입니다")
                .build();
        String gsonContent = gson.toJson(requestDto);

        CommentArtworkResDto responseDto = CommentArtworkResDto.builder()
                .createdAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .modifiedAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .commentId(1L)
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .content(requestDto.getContent())
                .build();

        Long artworkId = 1L;

        given(this.commentService.createCommentOnArtwork(Mockito.any(requestDto.getClass()),
                Mockito.any( member.getMemberId().getClass() ),
                Mockito.any(artworkId.getClass()),
                Mockito.any( gallery.getGalleryId().getClass() ) ) )
                .willReturn(new CommentArtworkHeadDto<>(1L,1L,responseDto));

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/galleries/{galleryId}/artworks/{artworkId}/comments",
                                gallery.getGalleryId(), 1L)
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonContent)
                /*.with(csrf())*/
        );
        commentService.createCommentOnArtwork(requestDto, member.getMemberId(), 1L, gallery.getGalleryId());

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.galleryId").value( gallery.getGalleryId()))
                .andExpect(jsonPath("$.artworkId").value( 1L ))
                .andExpect(jsonPath("$.commentList.createdAt").value("2022-11-25T11:09:24.94"))
                .andExpect(jsonPath("$.commentList.modifiedAt").value("2022-11-25T11:09:24.94"))
                .andExpect(jsonPath("$.commentList.commentId").value(1L))
                .andExpect(jsonPath("$.commentList.memberId").value(member.getMemberId()))
                .andExpect(jsonPath("$.commentList.nickname").value("test1"))
                .andExpect(jsonPath("$.commentList.content").value("댓글입니다"))


                .andDo(document(
                        "postCommentOnArtwork",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT - Access Token")
                                )
                        ),
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
    void testGetGalleryComment() throws Exception{
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

        Comment comment1 = commentRepository.save(Comment.builder()
                .commentId(1L)
                .content("댓글1")
                .member(member)
                .gallery(gallery)
                .artworkId(null)
                .commentStatus(CommentStatus.VALID)
                .build());

        Comment comment2 = commentRepository.save(Comment.builder()
                .commentId(2L)
                .content("댓글2")
                .member(member)
                .gallery(gallery)
                .artworkId(1L)
                .commentStatus(CommentStatus.VALID)
                .build());

        int page = 1; int size = 10;
        PageRequest pr = PageRequest.of(page - 1, size);

        given(this.commentService.findCommentByPage(
                Mockito.any( gallery.getGalleryId().getClass() ),
                Mockito.any(),
                eq(page),
                eq(size)))
                .willReturn(
                commentRepository.findAllByCommentStatusAndGallery_GalleryIdOrderByCommentIdDesc(VALID,1L, pr));

        Page<Comment> commentPage = commentService.findCommentByPage(1L, null, page, size);
        List<Comment> commentList = commentPage.getContent();
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, (int) commentPage.getTotalElements(), commentPage.getTotalPages());

        given(this.commentService.getGalleryCommentPage(
                Mockito.any( gallery.getGalleryId().getClass() ),
                eq(page),
                eq(size),
                Mockito.any( member.getMemberId().getClass() )))
                .willReturn(new CommentGalleryPageResponseDto<>(gallery.getGalleryId(),commentList,pageInfo));


        //test하려는 gallery request

        /*CommentArtworkResDto responseDto = CommentArtworkResDto.builder()
                .createdAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .modifiedAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .commentId(1L)
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .content(requestDto.getContent())
                .build();*/







    }

    @Test
    void getArtworkComment() {
    }

    @Test
    void patchComment() {
    }

    @Test
    void deleteComment() {
    }




}
