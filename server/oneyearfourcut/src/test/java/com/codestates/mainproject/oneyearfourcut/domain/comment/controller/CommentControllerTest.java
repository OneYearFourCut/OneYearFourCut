package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentArtworkResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentGalleryResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.CommentRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Role;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.oneyearfourcut.global.page.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
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
    void  testPostCommentOnGallery() throws Exception{
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("kang@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());
        String jwt = jwtTokenizer.testJwtGenerator(member);
        Gallery gallery = galleryRepository.save(new Gallery(1L));

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
    void testPostCommentOnArtwork() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("kang@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());
        String jwt = jwtTokenizer.testJwtGenerator(member);
        Gallery gallery = galleryRepository.save(new Gallery(1L));

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
                        )
                                ,pathParameters(
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
    void testGetGalleryComment() throws Exception{
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("kang@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());

        Gallery gallery = galleryRepository.save(Gallery.builder()
                .title("나의 전시관")
                .content("안녕하세요")
                .member(member)
                .status(GalleryStatus.OPEN)
                .build());

        int page = 1; int size = 10;
        PageRequest pr = PageRequest.of(page - 1, size);
        Page<Comment> commentPage = commentRepository.findAllByCommentStatusAndGallery_GalleryIdOrderByCommentIdDesc(VALID,1L, pr);

        given(this.commentService.findCommentByPage(
                Mockito.any( gallery.getGalleryId().getClass() ),
                Mockito.any(),
                eq(page),
                eq(size)))
                .willReturn(commentPage);

        Page<Comment> commentPage1 = commentService.findCommentByPage(1L, null, page, size);
        List<Comment> commentList = commentPage1.getContent();

        //임시로 생성, 추후 테스트 코드 고쳐야함
        List<CommentGalleryResDto> collect = commentList.stream()
                .map(comment -> {
                    return comment.toCommentGalleryResponseDto("/test.jpg"); //
                })
                .collect(Collectors.toList());
        //
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, (int) commentPage1.getTotalElements(), commentPage1.getTotalPages());

        given(this.commentService.getGalleryCommentPage(
                Mockito.any( gallery.getGalleryId().getClass() ),
                eq(page),
                eq(size)))
                .willReturn(new CommentGalleryPageResponseDto<>(gallery.getGalleryId(),collect,pageInfo));

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/galleries/{galleryId}/comments",
                               gallery.getGalleryId())
                        .param("page", String.valueOf(1))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                /*.with(csrf())*/
        );
        commentService.getGalleryCommentPage(gallery.getGalleryId(),1,10);

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.galleryId").value( gallery.getGalleryId()))
                .andExpect(jsonPath("$.commentList[0].createdAt").value("2022-11-14T23:41:52.764644"))
                .andExpect(jsonPath("$.commentList[0].modifiedAt").value("2022-11-16T23:41:57.764644"))
                .andExpect(jsonPath("$.commentList[0].commentId").value(4L))
                .andExpect(jsonPath("$.commentList[0].memberId").value(4L))
                .andExpect(jsonPath("$.commentList[0].nickname").value("reply"))
                .andExpect(jsonPath("$.commentList[0].content").value("comment444"))
                .andExpect(jsonPath("$.commentList[0].artworkId").doesNotExist())

                .andExpect(jsonPath("$.commentList[1].createdAt").value("2022-11-12T23:41:58.764644"))
                .andExpect(jsonPath("$.commentList[1].modifiedAt").value("2022-11-16T23:41:57.764644"))
                .andExpect(jsonPath("$.commentList[1].commentId").value(2L))
                .andExpect(jsonPath("$.commentList[1].memberId").value(1L))
                .andExpect(jsonPath("$.commentList[1].nickname").value("gallery"))
                .andExpect(jsonPath("$.commentList[1].content").value("comment2"))
                .andExpect(jsonPath("$.commentList[1].artworkId").value(2L))

                .andExpect(jsonPath("$.pageInfo.page").value("1"))
                .andExpect(jsonPath("$.pageInfo.size").value("10"))
                .andExpect(jsonPath("$.pageInfo.totalElements").value("3"))
                .andExpect(jsonPath("$.pageInfo.totalPages").value("1"))


                .andDo(document(
                                "getCommentOnGallery",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestParameters(parameterWithName("page").description("페이지 수")
                        )
                        ,pathParameters(
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
                                                fieldWithPath("commentList[].imagePath").type(JsonFieldType.STRING).description("작품 이미지 URL (없으면 null)"),

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
    void testGetArtworkComment() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("kang@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());

        Gallery gallery = galleryRepository.save(Gallery.builder()
                .title("나의 전시관")
                .content("안녕하세요")
                .member(member)
                .status(GalleryStatus.OPEN)
                .build());

        int page = 1; int size = 10; Long artworkId = 1L;
        PageRequest pr = PageRequest.of(page - 1, size);
        Page<Comment> commentPage = commentRepository.findAllByCommentStatusAndArtworkIdOrderByCommentIdDesc(VALID,1L, pr);

        given(this.commentService.findCommentByPage(
                Mockito.any( gallery.getGalleryId().getClass() ),
                Mockito.any(),
                eq(page),
                eq(size)))
                .willReturn(commentPage);

        Page<Comment> commentPage1 = commentService.findCommentByPage(1L, 1L, page, size);
        List<Comment> commentList = commentPage1.getContent();
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, (int) commentPage1.getTotalElements(), commentPage1.getTotalPages());
        List<CommentArtworkResDto> response = CommentArtworkResDto.toCommentArtworkResponseDtoList(commentList);

        given(this.commentService.getArtworkCommentPage(
                Mockito.any( gallery.getGalleryId().getClass() ), eq(artworkId),
                eq(page),
                eq(size)))
                .willReturn(new CommentArtworkPageResponseDto<>(gallery.getGalleryId(), 1L ,response,pageInfo));

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/galleries/{galleryId}/artworks/{artworkId}/comments",
                                gallery.getGalleryId(), 1L)
                        .param("page", String.valueOf(1))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                /*.with(csrf())*/
        );
        commentService.getArtworkCommentPage(gallery.getGalleryId(), 1L,1,10);

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.galleryId").value( gallery.getGalleryId()))
                .andExpect(jsonPath("$.artworkId").value( 1L ))
                .andExpect(jsonPath("$.commentList[0].commentId").value(1L))
                .andExpect(jsonPath("$.commentList[0].memberId").value(1L))
                .andExpect(jsonPath("$.commentList[0].nickname").value("gallery"))
                .andExpect(jsonPath("$.commentList[0].content").value("comment1댓글대스"))

                .andExpect(jsonPath("$.pageInfo.page").value("1"))
                .andExpect(jsonPath("$.pageInfo.size").value("10"))
                .andExpect(jsonPath("$.pageInfo.totalElements").value("1"))
                .andExpect(jsonPath("$.pageInfo.totalPages").value("1"))


                .andDo(document(
                                "getCommentOnArtwork",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestParameters(parameterWithName("page").description("페이지 수")
                                )
                                ,pathParameters(
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
    void testPatchComment() throws Exception {
        //given
        Member member3 = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("kang@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());
        String jwt = jwtTokenizer.testJwtGenerator(member3);

        Gallery gallery3 = galleryRepository.save(Gallery.builder()
                .title("나의 전시관")
                .content("안녕하세요")
                .member(member3)
                .status(GalleryStatus.OPEN)
                .build());

        Comment comment = commentRepository.save(Comment.builder()
                .content("오리지널댓글")
                .member(member3)
                .gallery(gallery3)
                .artworkId(null)
                .commentStatus(CommentStatus.VALID)
                .build());
        System.out.println("comment.getCommentId() = " + comment.getCommentId());
        CommentRequestDto requestDto = CommentRequestDto.builder()
                .content("수정댓글입니다.")
                .build();
        String gsonContent = gson.toJson(requestDto);

        Long commentId = 6L;

        given(this.commentService.findComment(commentId)).willReturn(comment);

        Optional<Comment> foundComment = commentRepository.findById(commentId);

        CommentGalleryResDto responseDto = CommentGalleryResDto.builder()
                .createdAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .modifiedAt(LocalDateTime.parse("2022-11-25T11:09:24.940"))
                .commentId(6L)
                .memberId(member3.getMemberId())
                .nickname(member3.getNickname())
                .content(requestDto.getContent())
                .artworkId(null)
                .build();

        given(this.commentService.modifyComment(Mockito.any( gallery3.getGalleryId().getClass()),
                eq(commentId),
                Mockito.any( requestDto.getClass() ),
                Mockito.any( member3.getMemberId().getClass() )))
                .willReturn(new CommentGalleryHeadDto<>( gallery3.getGalleryId() , responseDto));

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/galleries/{galleryId}/comments/{commentId}",
                                gallery3.getGalleryId(), comment.getCommentId())
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonContent)
                /*.with(csrf())*/
        );
        commentService.modifyComment(gallery3.getGalleryId(),responseDto.getCommentId(), requestDto, member3.getMemberId());
        System.out.println("gallery.getGalleryId() = " + gallery3.getGalleryId());

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.galleryId").value( gallery3.getGalleryId()))
                .andExpect(jsonPath("$.commentList.commentId").value( 6L ))
                .andExpect(jsonPath("$.commentList.memberId").value(member3.getMemberId()))
                .andExpect(jsonPath("$.commentList.nickname").value("test1"))
                .andExpect(jsonPath("$.commentList.content").value("수정댓글입니다."))
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
                                                fieldWithPath("commentList.imagePath").type(JsonFieldType.NULL).description("작품 이미지 url")
                                        )
                                )
                        )
                );
    }
    @Test
    void testDeleteComment() throws Exception{
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("kang@gmail.com")
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
                .commentId(6L)
                .content("댓글")
                .member(member)
                .gallery(gallery)
                .artworkId(1L)
                .commentStatus(CommentStatus.VALID)
                .build());

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/galleries/{galleryId}/comments/{commentId}",
                                gallery.getGalleryId(), comment.getCommentId())
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                /*.with(csrf())*/
        );

        //then
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
