package com.codestates.mainproject.oneyearfourcut.domain.alarm.event;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import lombok.Getter;

@Getter
public class AlarmEvent{//test
    private final Long receiverId;
    private final Long senderId;
    private final AlarmType alarmType;
    private final Long galleryId;
    private final Long artworkId;


    public AlarmEvent(Long receiverId, Long senderId, AlarmType alarmType, Long galleryId, Long artworkId) {
        this.alarmType = alarmType;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.galleryId = galleryId;
        this.artworkId = artworkId;
    }
}
