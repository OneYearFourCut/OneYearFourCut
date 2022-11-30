package com.codestates.mainproject.oneyearfourcut.domain.alarm.repository;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.Alarm;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE ALARM a set a.READ_CHECK =:readCheck where a.MEMBER_ID =:memberId", nativeQuery = true)
    void changeReadBoolean(@Param("readCheck") Boolean readCheck, @Param("memberId") Long memberId);

    Page<Alarm> findAllByMember_MemberIdOrderByAlarmIdDesc
            (Long memberId, Pageable pageable);
    Page<Alarm> findAllByAlarmTypeAndMember_MemberIdOrderByAlarmIdDesc
            (AlarmType alarmType, Long memberId, Pageable pageable);

    Boolean existsByMember_MemberIdAndReadCheck(Long memberId, Boolean readCheck);
}
