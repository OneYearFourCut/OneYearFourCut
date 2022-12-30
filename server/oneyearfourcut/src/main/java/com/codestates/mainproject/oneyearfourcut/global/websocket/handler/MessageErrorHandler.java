package com.codestates.mainproject.oneyearfourcut.global.websocket.handler;

import com.codestates.mainproject.oneyearfourcut.global.exception.dto.ErrorResponse;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
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
        if (errorMessage.equals("JWT signature")) {
            log.error("JWT 토큰 값과 관련한 에러 발생");
            return prepareErrorMessage(ErrorResponse.of(ExceptionCode.WRONG_ACCESS_TOKEN));
        }
        if (errorMessage.equals("JWT expired")) {
            return prepareErrorMessage(ErrorResponse.of(ExceptionCode.EXPIRED_ACCESS_TOKEN));
        }
        if (errorMessage.equals("Auth")) {
            return prepareErrorMessage(ErrorResponse.of(ExceptionCode.UNAUTHORIZED));
        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
}
    private Message<byte[]> prepareErrorMessage(ErrorResponse errorResponse) {
        String errorCode = errorResponse.toStringWithoutStatus();

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        accessor.setMessage(String.valueOf(errorResponse.getStatus()));
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(errorCode.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}
