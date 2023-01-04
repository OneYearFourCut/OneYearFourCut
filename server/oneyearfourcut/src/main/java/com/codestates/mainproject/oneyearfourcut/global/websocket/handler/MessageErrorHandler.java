package com.codestates.mainproject.oneyearfourcut.global.websocket.handler;

import com.codestates.mainproject.oneyearfourcut.global.exception.dto.ErrorResponse;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@Slf4j
public class MessageErrorHandler extends StompSubProtocolErrorHandler {

    public MessageErrorHandler() {
        super();
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        String errorMessage = ex.getCause().getMessage();
        if (errorMessage.startsWith("잘못된")) {
            log.error("WRONG_ACCESS_TOKEN");
            return prepareErrorMessage(clientMessage, ErrorResponse.of(ExceptionCode.WRONG_ACCESS_TOKEN));
        }
        if (errorMessage.startsWith("expired")) {
            log.error("EXPIRED_ACCESS_TOKEN");
            return prepareErrorMessage(clientMessage, ErrorResponse.of(ExceptionCode.EXPIRED_ACCESS_TOKEN));
        }
        if (errorMessage.startsWith("Access")) {
            log.error("NO_ACEESS_TOKEN");
            return prepareErrorMessage(clientMessage, ErrorResponse.of(ExceptionCode.NO_ACEESS_TOKEN));
        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
}
    private Message<byte[]> prepareErrorMessage(Message<byte[]> clientMessage, ErrorResponse errorResponse) {
        String errorCode = String.valueOf(errorResponse.getStatus());
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        String errorResponseMessage = null;
        try {
            errorResponseMessage = mapper.writeValueAsString(errorResponse);

        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace(); // 실행되지 않지만 Exception catch를 위해 작성
        }

        accessor.setMessage(errorResponseMessage);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(clientMessage.getPayload(), accessor.getMessageHeaders());
    }
}
