package com.codestates.mainproject.oneyearfourcut.domain.gallery.controller;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryPatchDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.PrincipalDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GalleryController.class)
@MockBean({JpaMetamodelMappingContext.class, ClientRegistrationRepository.class})
@AutoConfigureRestDocs
class GalleryControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GalleryService galleryService;
    @Autowired
    private Gson gson;


    private String jwt = "Bearer test1234test1234";
    @TestConfiguration
    static class testSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf().disable()
                    .build();
        }
    }
    @BeforeAll
    public static void setup() {
        //security context holder
        String username = "test";
        long id = 1L;
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(new PrincipalDto(username, id), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void postGallery() throws Exception {
        //given
        String content = gson.toJson(GalleryRequestDto.builder()
                .title("???????????? ?????????")
                .content("???????????????")
                .build());

        GalleryResponseDto responseDto = GalleryResponseDto.builder()
                .galleryId(1L)
                .title("???????????? ?????????")
                .content("???????????????")
                .createdAt(LocalDateTime.now())
                .build();

        given(galleryService.createGallery(any(GalleryRequestDto.class), anyLong()))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/galleries")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.galleryId").value(responseDto.getGalleryId()))
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.createdAt").value(responseDto.getCreatedAt().toString()))
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
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ??????")
                                )
                        )
                        , responseFields(
                                List.of(
                                        fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("????????????")
                                )
                        )
                ));
    }

    @Test
    void getGallery() throws Exception {
        //given
        Gallery gallery = Gallery.builder()
                .title("???????????? ?????????")
                .content("???????????????")
                .build();
        gallery.generateTestGallery(1L, LocalDateTime.now());

        given(galleryService.findGallery(1L))
                .willReturn(gallery);

        //when
        ResultActions actions = mockMvc.perform(
                get("/galleries/{galleryId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.galleryId").value(gallery.getGalleryId()))
                .andExpect(jsonPath("$.title").value(gallery.getTitle()))
                .andExpect(jsonPath("$.content").value(gallery.getContent()))
//                .andExpect(jsonPath("$.createdAt").value(String.valueOf(gallery.getCreatedAt())))
                .andDo(document(
                        "getGallery",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("galleryId").description("Gallery ?????????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("????????????")
                                )
                        )
                ));
    }

    @Test
    void patchGallery() throws Exception {
        //given
        String content = gson.toJson(GalleryPatchDto.builder()
                .title("????????? ??????")
                .content("????????? ??????")
                .build());

        GalleryResponseDto galleryResponseDto = Gallery.builder()
                .title("????????? ??????")
                .content("????????? ??????")
                .build()
                .toGalleryResponseDto();

        given(galleryService.modifyGallery(any(GalleryPatchDto.class), anyLong()))
                .willReturn(galleryResponseDto);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/galleries/me")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("????????? ?????? ??????"))
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
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ?????? (?????? ??????)"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ?????? (?????? ??????)")
                                )
                        )
                ));
    }

    @Test
    void deleteGallery() throws Exception {
        //given
        willDoNothing().given(galleryService).deleteGallery(anyLong());

        //when
        ResultActions actions = mockMvc.perform(
                delete("/galleries/me")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isNoContent())
                .andExpect(jsonPath("$").value("????????? ?????? ??????"))
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