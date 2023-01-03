package com.codestates.mainproject.oneyearfourcut.global.websocket.handler;

import com.codestates.mainproject.oneyearfourcut.global.exception.dto.ErrorResponse;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
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
        if (errorMessage.equals("잘못된")) {
            log.error("WRONG_ACCESS_TOKEN");
            return prepareErrorMessage(ErrorResponse.of(ExceptionCode.WRONG_ACCESS_TOKEN));
        }
        if (errorMessage.equals("expired")) {
            log.error("EXPIRED_ACCESS_TOKEN");
            return prepareErrorMessage(ErrorResponse.of(ExceptionCode.EXPIRED_ACCESS_TOKEN));
        }
        if (errorMessage.equals("Access")) {
            log.error("NO_ACEESS_TOKEN");
            return prepareErrorMessage(ErrorResponse.of(ExceptionCode.NO_ACEESS_TOKEN));
        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
}
    private Message<byte[]> prepareErrorMessage(ErrorResponse errorResponse) {
        String errorCode = String.valueOf(errorResponse.getStatus());
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        accessor.setMessage(String.valueOf(errorResponse.getStatus())); // 수정된 부분
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(errorCode.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}
