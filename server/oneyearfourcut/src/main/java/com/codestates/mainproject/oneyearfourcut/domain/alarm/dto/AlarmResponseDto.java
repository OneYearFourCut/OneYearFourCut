package com.codestates.mainproject.oneyearfourcut.domain.alarm.dto;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.Alarm;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmResponseDto {
    private Long alarmId;
    private String alarmType;
    private String userNickname;
    private LocalDateTime createdAt;
    private Boolean read;
    private Long galleryId;
    private Long artworkId;
    private String artworkTitle;

}