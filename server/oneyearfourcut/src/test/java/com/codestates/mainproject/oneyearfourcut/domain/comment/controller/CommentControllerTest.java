package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentGalleryResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.xml.transform.Result;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
/*@WebMvcTest(CommentController.class)*/
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private Comment comment;
    @Autowired
    private Gson gson;

    @Test
    void postCommentOnGallery() throws Exception{
        //Given
        CommentRequestDto requestDto = new CommentRequestDto("Sample Content");
        String content = gson.toJson(requestDto);

/*        CommentGalleryResDto.builder()
                .content("Sample Content")
                .build();
        given(commentService.createCommentOnGallery(
                requestDto
                ,Mockito.any(Gallery.class).getGalleryId()
                ,Mockito.any(Member.class).getMemberId()));*/

        //When

        ResultActions actions = mockMvc.perform(
                post("/galleries/{gallery-id}/comments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //Then
    }

    @Test
    void postCommentOnArtwork() throws Exception{
        //Given

        //When

        //Then
    }

    @Test
    void getGalleryComment() throws Exception{

    }


}
