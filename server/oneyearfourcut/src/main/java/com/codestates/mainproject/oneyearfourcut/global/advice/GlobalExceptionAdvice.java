package com.codestates.mainproject.oneyearfourcut.global.advice;

import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.dto.ErrorResponse;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity handleException(BusinessLogicException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
        log.error("# handleException: {}", e.getMessage());
        return ResponseEntity.status(e.getExceptionCode().getStatus()).body(response);
    }

    @ExceptionHandler
    public ResponseEntity handleUploadFileException(MaxUploadSizeExceededException e) {
        final ErrorResponse response = ErrorResponse.of(ExceptionCode.EXCEEDED_FILE_SIZE);
        log.error("# handleUploadFileException: ", e);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}