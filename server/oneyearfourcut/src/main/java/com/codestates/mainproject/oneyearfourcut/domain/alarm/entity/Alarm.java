package com.codestates.mainproject.oneyearfourcut.domain.alarm.entity;

import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Alarm extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length = 12)
    private AlarmType alarmType;

    @Column
    private Long targetPlaceId;

    @Column
    private Long byWhomMemberId;

    @Column
    private Boolean readCheck;



}
