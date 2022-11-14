package com.codestates.mainproject.oneyearfourcut.global.exception.dto;

import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String exception;
    private String message;

    public static ErrorResponse of(ExceptionCode exceptionCode) {
        return new ErrorResponse(exceptionCode.getStatus(), exceptionCode.name(), exceptionCode.getMessage());
    }
}
