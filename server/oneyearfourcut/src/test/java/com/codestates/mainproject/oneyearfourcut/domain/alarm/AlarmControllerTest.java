package com.codestates.mainproject.oneyearfourcut.domain.alarm;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.dto.AlarmReadCheckResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.dto.AlarmResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.Alarm;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.repository.AlarmRepository;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Role;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
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
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

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

@Transactional // ?????????????????? ?????????????????????. mock?????? ???????????? ??????????????????.
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WithMockUser(username = "test@gmail.com", password = "0000")
class AlarmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private AlarmRepository alarmRepository;

    @MockBean
    private AlarmService alarmService;

    /*@Test
    void getAlarmListFiltered() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("kang@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());
        String jwt = jwtTokenizer.testJwtGenerator(member);

        Alarm alarm1 = alarmRepository.save(Alarm.builder()
                .member(member)
                .memberIdProducer(3L)
                .alarmType(AlarmType.POST_ARTWORK)
                .artworkId(1L)
                .artworkTitle("Test ?????? ?????????.")
                .userNickname("???????????? 1")
                .readCheck(false)
                .build());

        Alarm alarm2 = alarmRepository.save(Alarm.builder()
                .member(member)
                .memberIdProducer(2L)
                .alarmType(AlarmType.LIKE_ARTWORK)
                .artworkId(1L)
                .artworkTitle("Test ?????? ?????????.")
                .userNickname("???????????? 2")
                .readCheck(false)
                .build());

        Alarm alarm3 = alarmRepository.save(Alarm.builder()
                .member(member)
                .memberIdProducer(3L)
                .alarmType(AlarmType.COMMENT_GALLERY)
                .artworkId(null)
                .artworkTitle(null)
                .userNickname("???????????? 3")
                .readCheck(true)
                .build());

        List<Alarm> alarmList = new ArrayList<>();
        alarmList.add(alarm1);
        alarmList.add(alarm2);
        alarmList.add(alarm3);

        int page = 1;
        given(this.alarmService.getAlarmPagesByFilter(
                Mockito.any(String.class), eq(page), Mockito.any(member.getMemberId().getClass())))
                .willReturn(AlarmResponseDto.toAlarmResponseDtoList(alarmList));

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/members/me/alarms")
                        .param("page", String.valueOf(1))
                        .param("filter", "ALL")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        alarmService.getAlarmPagesByFilter("ALL", 1, member.getMemberId());

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].alarmId").value(alarm1.getAlarmId()))
                .andExpect(jsonPath("$.[0].alarmType").value("POST_ARTWORK"))
                .andExpect(jsonPath("$.[0].userNickname").value("???????????? 1"))
                .andExpect(jsonPath("$.[0].read").value(false))
                .andExpect(jsonPath("$.[0].artworkId").value(alarm1.getArtworkId()))
                .andExpect(jsonPath("$.[0].artworkTitle").value(alarm1.getArtworkTitle()))

                .andExpect(jsonPath("$.[1].alarmId").value(alarm2.getAlarmId()))
                .andExpect(jsonPath("$.[1].alarmType").value("LIKE_ARTWORK"))
                .andExpect(jsonPath("$.[1].userNickname").value("???????????? 2"))
                .andExpect(jsonPath("$.[1].read").value(false))
                .andExpect(jsonPath("$.[1].artworkId").value(alarm2.getArtworkId()))
                .andExpect(jsonPath("$.[1].artworkTitle").value(alarm2.getArtworkTitle()))

                .andExpect(jsonPath("$.[2].alarmId").value(alarm3.getAlarmId()))
                .andExpect(jsonPath("$.[2].alarmType").value("COMMENT_GALLERY"))
                .andExpect(jsonPath("$.[2].userNickname").value("???????????? 3"))
                .andExpect(jsonPath("$.[2].read").value(true))
                .andExpect(jsonPath("$.[2].artworkId").doesNotExist())
                .andExpect(jsonPath("$.[2].artworkTitle").doesNotExist())

                .andDo(document(
                                "getAlarmListFiltered",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        List.of(
                                                headerWithName("Authorization").description("JWT - Access Token")
                                        )
                                )
                                , requestParameters(parameterWithName("filter").description("?????? ??????"), parameterWithName("page").description("????????? ???")
                                )


                                , responseFields(
                                        List.of(

                                                fieldWithPath("[].alarmId").type(JsonFieldType.NUMBER).description("?????? ID"),
                                                fieldWithPath("[].alarmType").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("[].userNickname").type(JsonFieldType.STRING).description("????????? ?????? ?????? ?????? ?????????"),
                                                fieldWithPath("[].read").type(JsonFieldType.BOOLEAN).description("?????? ??????"),
                                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("[].artworkId").type(JsonFieldType.NUMBER).description("?????? ID").optional(),
                                                fieldWithPath("[].artworkTitle").type(JsonFieldType.STRING).description("?????? ??????").optional()

                                        )
                                )
                        )
                );
    }*/

    @Test
    void checkReadAlarm() throws Exception {
        //given
        Member member2 = memberRepository.save(Member.builder()
                .nickname("test2")
                .email("kang@gmail.com")
                .role(Role.USER)
                .profile("/path")
                .status(ACTIVE)
                .build());
        String jwt = jwtTokenizer.testJwtGenerator(member2);

        Alarm alarm4 = alarmRepository.save(Alarm.builder()
                .member(member2)
                .memberIdProducer(3L)
                .alarmType(AlarmType.POST_ARTWORK)
                .artworkId(1L)
                .artworkTitle("Test ?????? ?????????.")
                .userNickname("???????????? 1")
                .readCheck(false)
                .build());

        given(this.alarmService.checkReadAlarm(Mockito.any(member2.getMemberId().getClass())))
                .willReturn(AlarmReadCheckResponseDto.builder().readAlarmExist(Boolean.FALSE).message("?????? ????????? ????????????.").build());

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/members/me/alarms/read")
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        alarmService.checkReadAlarm(member2.getMemberId());

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.readAlarmExist").value(false))
                .andExpect(jsonPath("$.message").value("?????? ????????? ????????????."))
                .andDo(document(
                                "checkReadAlarm",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        List.of(
                                                headerWithName("Authorization").description("JWT - Access Token")
                                        )
                                ),
                                responseFields(
                                        List.of(
                                                fieldWithPath(".readAlarmExist").type(JsonFieldType.BOOLEAN).description("?????? ?????? ?????? ????????????"),
                                                fieldWithPath(".message").type(JsonFieldType.STRING).description("?????? ???????????? ?????????")
                                        )
                                )
                        )
                );
    }
}