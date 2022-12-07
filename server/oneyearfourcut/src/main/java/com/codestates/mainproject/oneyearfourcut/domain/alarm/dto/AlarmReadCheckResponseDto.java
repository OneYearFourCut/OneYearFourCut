package com.codestates.mainproject.oneyearfourcut.domain.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmReadCheckResponseDto {
    private Boolean readAlarmExist;
    private String message;
}
