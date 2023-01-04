package com.codestates.mainproject.oneyearfourcut.global.websocket.handler;

import com.codestates.mainproject.oneyearfourcut.global.exception.dto.ErrorResponse;
import com.codestates.mainproject.oneyearfourcut.global.exception.dto.MessageErrorResponse;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

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
        Object payloadFromRequest = new String(clientMessage.getPayload(), StandardCharsets.UTF_8);
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        MessageErrorResponse<Object> messageErrorResponse = MessageErrorResponse.builder().errorResponse(errorResponse).payload(payloadFromRequest).build();

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonContent = gson.toJson(messageErrorResponse);

        byte[] payload = jsonContent.getBytes();

        accessor.setContentType(MimeType.valueOf(ContentType.APPLICATION_JSON.getMimeType()));
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(payload, accessor.getMessageHeaders());
    }
}
