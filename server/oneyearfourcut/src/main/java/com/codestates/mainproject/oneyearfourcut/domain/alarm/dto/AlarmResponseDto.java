package com.codestates.mainproject.oneyearfourcut.domain.alarm.dto;

import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmResponseDto extends Auditable {
    private Long alarmId;
    private Long memberId;
    private String alarmType;
    private Long targetPlaceId;
    private Long byWhomMemberId;
    private Boolean readCheck;
}
