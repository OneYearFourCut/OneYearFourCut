package com.codestates.mainproject.oneyearfourcut.domain.alarm.repository;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
