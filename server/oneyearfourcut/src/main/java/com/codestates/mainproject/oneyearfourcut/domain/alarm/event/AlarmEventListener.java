package com.codestates.mainproject.oneyearfourcut.domain.alarm.event;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.sse.SseType;
import com.codestates.mainproject.oneyearfourcut.domain.sse.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventListener {
    private final AlarmService alarmService;
    private final SseService sseService;

    @EventListener
    public void handleAlarmEvent(AlarmEvent event) {
        alarmService.createAlarm(event.getReceiverId(), event.getSenderId(), event.getAlarmType(), event.getGalleryId(), event.getArtworkId());
        sseService.send(event.getReceiverId(), SseType.ALARM, true);
    }

}
