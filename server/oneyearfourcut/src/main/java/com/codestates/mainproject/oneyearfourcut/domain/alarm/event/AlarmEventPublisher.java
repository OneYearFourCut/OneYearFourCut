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
        applicationEventPublisher.publishEvent(alarmEvent);
    }
}
