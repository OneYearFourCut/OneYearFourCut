package com.codestates.mainproject.oneyearfourcut.global.exception.dto;

import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String exception;
    private String message;

    public static ErrorResponse of(ExceptionCode exceptionCode) {
        return new ErrorResponse(exceptionCode.getStatus(), exceptionCode.name(), exceptionCode.getMessage());
    }
    public static ErrorResponse of(HttpStatus httpStatus) {
        return new ErrorResponse(httpStatus.value(), httpStatus.name(), httpStatus.getReasonPhrase());
    }


    public String toStringWithoutStatus() { // status를 제외한 toString()
        return "ErrorResponse{" +
                ", exception='" + exception + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
