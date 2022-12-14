package com.codestates.mainproject.oneyearfourcut.domain.alarm.entity;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.dto.AlarmResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "alarm")
public class Alarm extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long alarmId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column
    private Long memberIdProducer;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private AlarmType alarmType;

    @Column
    private Long artworkId;
    @Column
    private String artworkTitle;

    @Column
    private String userNickname;

    @Column(name = "READ_CHECK")
    private Boolean readCheck;

    @Column
    private Long galleryId;

    public AlarmResponseDto toAlarmResponseDto() {
        return AlarmResponseDto.builder()
                .alarmId(this.alarmId)
                .alarmType(String.valueOf(this.getAlarmType()))
                .userNickname(this.getUserNickname())
                .createdAt(this.getCreatedAt())
                .read(this.getReadCheck())
                .galleryId(this.galleryId)
                .artworkId(this.artworkId)
                .artworkTitle(this.artworkTitle)
                .build();
    }
    public void checkRead() {
        this.readCheck = true;
    }
}
