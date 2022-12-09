package com.codestates.mainproject.oneyearfourcut.domain.alarm.controller;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.dto.AlarmResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members/me/alarms")
@Validated
@Slf4j
@AllArgsConstructor
public class AlarmController {
    //test
    private final AlarmService alarmService;
    @GetMapping
    public ResponseEntity<Object> getAlarmListFiltered(@RequestParam String filter, @RequestParam int page,
                                                       @LoginMember Long memberId){
        return new ResponseEntity<>(alarmService.getAlarmPagesByFilter(filter, page, memberId), HttpStatus.OK);
    }
    @GetMapping("/read")
    public ResponseEntity<Object> checkReadAlarm(@LoginMember Long memberId){
        return new ResponseEntity<>(alarmService.checkReadAlarm(memberId), HttpStatus.OK);
    }

}
