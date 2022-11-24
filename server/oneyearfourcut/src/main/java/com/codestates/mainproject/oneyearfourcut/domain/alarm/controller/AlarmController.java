package com.codestates.mainproject.oneyearfourcut.domain.alarm.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members/me/alarms")
@Validated
@Slf4j
@AllArgsConstructor
public class AlarmController {
}
