package com.codestates.mainproject.oneyearfourcut.domain.alarm.event;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishAlarmEvent(AlarmEvent alarmEvent) {
        if (alarmEvent.getSenderId() != alarmEvent.getReceiverId()) {   //보내는 이와 받는이가 같은사람이면 보내지 않음
            applicationEventPublisher.publishEvent(alarmEvent);
        }
    }
}
