package com.codestates.mainproject.oneyearfourcut.domain.alarm.event;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AlarmEvent{
    private final Long receiverId;
    private final Long senderId;
    private final AlarmType alarmType;
    private final Long galleryId;
    private final Long artworkId;

    @Builder
    public AlarmEvent(Long receiverId, Long senderId, AlarmType alarmType, Long galleryId, Long artworkId) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.alarmType = alarmType;
        this.galleryId = galleryId;
        this.artworkId = artworkId;
    }
}
