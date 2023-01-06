package com.codestates.mainproject.oneyearfourcut.global.exception.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageErrorResponse<T> {

    private ErrorResponse errorResponse;
    private T payload;

    @Builder
    public MessageErrorResponse(ErrorResponse errorResponse, T payload) {
        this.errorResponse = errorResponse;
        this.payload = payload;
    }
}
