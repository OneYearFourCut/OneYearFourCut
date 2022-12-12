package com.codestates.mainproject.oneyearfourcut.domain.alarm.event;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishAlarmEvent(Long receiverId, Long senderId, AlarmType alarmType, Long galleryId, Long artworkId) {
        AlarmEvent alarmEvent = new AlarmEvent(receiverId, senderId, alarmType, galleryId, artworkId);
        applicationEventPublisher.publishEvent(alarmEvent);
    }
}
