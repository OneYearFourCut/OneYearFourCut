package com.codestates.mainproject.oneyearfourcut.domain.artwork.controller;

import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.OneYearFourCutResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.PrincipalDto;
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
import org.springframework.mock.web.MockHttpServletRequest;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;

import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.mainproject.oneyearfourcut.global.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtworkController.class)
@MockBean({JpaMetamodelMappingContext.class, ClientRegistrationRepository.class})
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
    @WithMockUser(username = "test@gmail.com", password = "0000")
    void postArtworkTest() throws Exception {
        Long memberId = 1L;
        Long galleryId = 1L;

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "image.png",
                "image/png",
                "<<image.png>>".getBytes());

        String title = "올해 네 컷 - D25";
        String content = "화이팅!";

        ArtworkRequestDto requestDto = ArtworkRequestDto.builder().image(image).title(title).content(content).build();
        String request = gson.toJson(requestDto);

        willDoNothing().given(artworkService).createArtwork(memberId, galleryId, requestDto);

        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.multipart("/galleries/{gallery-id}/artworks", galleryId)
                                .file(image)
                                .param("title", title)
                                .param("content", content)
                                .header("Authorization", "Bearer (AccessToken)")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .flashAttr("write", requestDto)
                );
        actions
                .andExpect(status().isCreated())
                .andDo(document("post-artwork",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT - Access Token")
                        ),
                        pathParameters(
                                parameterWithName("gallery-id").description("전시관 식별자")
                        )
                        , requestParameters(
                                parameterWithName("title").description("작품에 대한 제목"),
                                parameterWithName("content").description("작품에 대한 설명")
                        ),
                        requestParts(
                                partWithName("image").description("이미지 파일 첨부")
                        )

                ));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", password = "0000")
    void getArtworks() throws Exception {
        Long galleryAdmin = 1L;
        Long galleryId = 1L;

        Gallery gallery = new Gallery(galleryAdmin);

        Artwork artwork1 = Artwork.builder().artworkId(1L).title("(1) 작품 제목").content("(1) 설명").imagePath("/1.png").build();
        artwork1.setMember(new Member(2L));
        artwork1.setGallery(gallery);

        Artwork artwork2 = Artwork.builder().artworkId(2L).title("(2) 작품 제목").content("조금만 더 힘내요!").imagePath("/2.png").build();
        artwork2.setMember(new Member(3L));
        artwork2.setGallery(gallery);

        Artwork artwork3 = Artwork.builder().artworkId(3L).title("(3) 작품 제목").content("화이팅!").imagePath("/3.png").build();
        artwork3.setMember(new Member(4L));
        artwork3.setGallery(gallery);

        Artwork artwork4 = Artwork.builder().artworkId(4L).title("(4) 작품 제목").content("화이팅!").imagePath("/4.png").build();
        artwork4.setMember(new Member(5L));
        artwork4.setGallery(gallery);

        List<Artwork> artworkList = List.of(artwork4, artwork3, artwork2, artwork1);

        List<ArtworkResponseDto> responseListDto = ArtworkResponseDto.toListResponse(artworkList);

        given(artworkService.findArtworkList(any(Long.class))).willReturn(responseListDto);

        ResultActions actions =
                mockMvc.perform(
                        get("/galleries/{gallery-id}/artworks", galleryId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer (accessToken)")
                );
        actions
                .andExpect(status().isOk())
                .andDo(document("get-artworks",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        headerWithName("Authorization").description("JWT - Access Token")
                                ),
                                pathParameters(
                                        parameterWithName("gallery-id").description("전시관 식별자")
                                ),
                                responseFields(
                                        fieldWithPath("[].artworkId").type(JsonFieldType.NUMBER).description("작품 식별자"),
                                        fieldWithPath("[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("작품에 대한 제목"),
                                        fieldWithPath("[].content").type(JsonFieldType.STRING).description("작품에 대한 설명"),
                                        fieldWithPath("[].imagePath").type(JsonFieldType.STRING).description("작품 이미지 경로"),
                                        fieldWithPath("[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 수"),
                                        fieldWithPath("[].liked").type(JsonFieldType.BOOLEAN).description("작품에 대한 로그인 유저의 좋아요 확인"),
                                        fieldWithPath("[].commentCount").type(JsonFieldType.NUMBER).description("댓글 수")
                                )
                        )
                );
    }

    @Test
    @WithMockUser(username = "test@gmail.com", password = "0000")
    void getArtwork() throws Exception {
        Long memberId = 1L;
        Long galleryId = 1L;
        Long artworkId = 1L;

        Member writer = new Member(1L);
        Member loginMember = new Member(2L);
        Gallery gallery = new Gallery(1L);

        Artwork findArtwork = Artwork.builder().artworkId(artworkId).title("올해 네 컷 - D25").content("화이팅!").imagePath("/1.png").build();
        findArtwork.setMember(writer);
        findArtwork.setGallery(gallery);

        ArtworkLike like = new ArtworkLike(1L);
        like.setMember(loginMember);
        like.setArtwork(findArtwork);

        Comment comment = Comment.builder().commentId(1L).member(loginMember).gallery(gallery).artworkId(artworkId).build();

        ArtworkResponseDto responseDto = findArtwork.toArtworkResponseDto();

        String response = gson.toJson(responseDto);

        given(artworkService.findArtwork(any(Long.class), any(Long.class))).willReturn(responseDto);

        ResultActions actions =
                mockMvc.perform(
                        get("/galleries/{gallery-id}/artworks/{artwork-id}", galleryId, artworkId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer (accessToken)")
                );
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artworkId").value(responseDto.getArtworkId()))
                .andExpect(jsonPath("$.memberId").value(responseDto.getMemberId()))
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
                                        headerWithName("Authorization").description("JWT - Access Token")
                                ),
                                pathParameters(
                                        parameterWithName("gallery-id").description("전시관 식별자"),
                                        parameterWithName("artwork-id").description("작품 식별자")
                                ),
                                responseFields(
                                        fieldWithPath("artworkId").type(JsonFieldType.NUMBER).description("작품 식별자"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("작품에 대한 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("작품에 대한 설명"),
                                        fieldWithPath("imagePath").type(JsonFieldType.STRING).description("작품 이미지 경로"),
                                        fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("좋아요 수"),
                                        fieldWithPath("liked").type(JsonFieldType.BOOLEAN).description("작품에 대한 로그인 유저의 좋아요 확인"),
                                        fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("댓글 수")

                                )
                        )
                );
    }

    @Test
    @WithMockUser(username = "test@gmail.com", password = "0000")
    void getOneYearFourCut() throws Exception {
        Long galleryAdmin = 1L;
        Long galleryId = 1L;

        Gallery gallery = new Gallery(galleryAdmin);

        Artwork artwork1 = Artwork.builder().artworkId(1L).title("(1) 작품 제목").content("(1) 설명").imagePath("/1.png").build();
        artwork1.setMember(new Member(2L));
        artwork1.setGallery(gallery);
        ArtworkLike like1 = new ArtworkLike(1L);
        like1.setArtwork(artwork1);

        Artwork artwork2 = Artwork.builder().artworkId(2L).title("(2) 작품 제목").content("조금만 더 힘내요!").imagePath("/2.png").build();
        artwork2.setMember(new Member(3L));
        artwork2.setGallery(gallery);
        ArtworkLike like2 = new ArtworkLike(2L);
        like2.setArtwork(artwork2);
        ArtworkLike like3 = new ArtworkLike(3L);
        like3.setArtwork(artwork2);

        Artwork artwork3 = Artwork.builder().artworkId(3L).title("(3) 작품 제목").content("화이팅!").imagePath("/3.png").build();
        artwork3.setMember(new Member(4L));
        artwork3.setGallery(gallery);
        ArtworkLike like4 = new ArtworkLike(4L);
        like4.setArtwork(artwork3);
        ArtworkLike like5 = new ArtworkLike(5L);
        like5.setArtwork(artwork3);
        ArtworkLike like6 = new ArtworkLike(6L);
        like6.setArtwork(artwork3);

        Artwork artwork4 = Artwork.builder().artworkId(4L).title("(4) 작품 제목").content("화이팅!").imagePath("/4.png").build();
        artwork4.setMember(new Member(5L));
        artwork4.setGallery(gallery);
        ArtworkLike like7 = new ArtworkLike(7L);
        like7.setArtwork(artwork4);
        ArtworkLike like8 = new ArtworkLike(8L);
        like8.setArtwork(artwork4);
        ArtworkLike like9 = new ArtworkLike(9L);
        like9.setArtwork(artwork4);
        ArtworkLike like10 = new ArtworkLike(10L);
        like10.setArtwork(artwork4);

        List<Artwork> artworkList = List.of(artwork4, artwork3, artwork2, artwork1);

        List<OneYearFourCutResponseDto> responseListDto = OneYearFourCutResponseDto.toListResponse(artworkList);

        given(artworkService.findOneYearFourCut(any(Long.class))).willReturn(responseListDto);

        ResultActions actions =
                mockMvc.perform(
                        get("/galleries/{gallery-id}/artworks/like", galleryId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer (accessToken)")
                );
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-oneYearFourCut",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        headerWithName("Authorization").description("JWT - Access Token")
                                ),
                                pathParameters(
                                        parameterWithName("gallery-id").description("전시관 식별자")
                                ),
                                responseFields(
                                        fieldWithPath("[].artworkId").type(JsonFieldType.NUMBER).description("작품 식별자"),
                                        fieldWithPath("[].imagePath").type(JsonFieldType.STRING).description("작품 이미지 경로"),
                                        fieldWithPath("[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 수")
                                )
                        )
                );
    }

    @Test
    @WithMockUser(username = "test@gmail.com", password = "0000")
    void patchArtworkTest() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "image.png",
                "image/png",
                "<<image.png>>".getBytes());
        String title = "수정된 제목";
        String content = "수정된 설명";

        Long memberId = 1L;
        Long galleryId = 1L;
        Long artworkId = 1L;

        Member member = new Member(memberId);
        Gallery gallery = new Gallery(galleryId);

        Artwork artwork = Artwork.builder().artworkId(1L).title("(1) 작품 제목").content("(1) 설명").imagePath("/1.png").build();
        artwork.setMember(member);
        artwork.setGallery(gallery);

        ArtworkRequestDto requestDto = ArtworkRequestDto.builder().image(image).title(title).content(content).build();
        Artwork modifyArtwork = requestDto.toEntity();

        artwork.modify(modifyArtwork);

        ArtworkResponseDto response = artwork.toArtworkResponseDto();

        given(artworkService.updateArtwork(any(Long.class), any(Long.class), any(Long.class), any(ArtworkRequestDto.class))).willReturn(response);

        MockMultipartHttpServletRequestBuilder builder =
                RestDocumentationRequestBuilders.
                        multipart("/galleries/{gallery-id}/artworks/{artwork-id}", galleryId, artworkId);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
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
                .andExpect(jsonPath("$.title").value(modifyArtwork.getTitle()))
                .andExpect(jsonPath("$.content").value(modifyArtwork.getContent()))
                .andExpect(jsonPath("$.imagePath").value(artwork.getImagePath()))
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
                                parameterWithName("gallery-id").description("전시관 식별자"),
                                parameterWithName("artwork-id").description("작품 식별자")
                        )
                        , requestParameters(
                                parameterWithName("title").description("수정할 작품 제목").optional(),
                                parameterWithName("content").description("수정할 작품 설명").optional()
                        ),
                        requestParts(
                                partWithName("image").description("수정할 이미지").optional()
                        ),
                        responseFields(
                                fieldWithPath("artworkId").type(JsonFieldType.NUMBER).description("작품 식별자"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("작품에 대한 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("작품에 대한 설명"),
                                fieldWithPath("imagePath").type(JsonFieldType.STRING).description("작품 이미지 경로"),
                                fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("좋아요 수"),
                                fieldWithPath("liked").type(JsonFieldType.BOOLEAN).description("작품에 대한 로그인 유저의 좋아요 확인"),
                                fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("댓글 수")
                        )
                ));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", password = "0000")
    void deleteArtworkTest() throws Exception {
        Long memberId = 1L;
        Long galleryId = 1L;
        Long artworkId = 1L;

        willDoNothing().given(artworkService).deleteArtwork(any(Long.class), any(Long.class), any(Long.class));

        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/galleries/{gallery-id}/artworks/{artwork-id}", galleryId, artworkId)
                                .header("Authorization", "Bearer (AccessToken)")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
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
                                parameterWithName("gallery-id").description("전시관 식별자"),
                                parameterWithName("artwork-id").description("작품 식별자")
                        )
                ));
    }
}