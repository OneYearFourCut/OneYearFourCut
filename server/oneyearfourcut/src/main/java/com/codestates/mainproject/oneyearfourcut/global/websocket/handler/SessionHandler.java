package com.codestates.mainproject.oneyearfourcut.global.websocket.handler;

import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@Slf4j
public class SessionHandler extends StompSessionHandlerAdapter {

    private StompSession session;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("New session established : " + session.getSessionId());
        this.session = session;
        log.info("connectedHeaders - Destination : {}", connectedHeaders.getDestination());

        subscribe(connectedHeaders.getDestination());
        log.info("connectedHeaders - 구독 성공~ : {}", connectedHeaders.getDestination());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Got an exception. Reason : {}", exception.getMessage());
        log.error("Command : {}", command.toString());
        log.info("session : {}", session.getSessionId());
        log.info("sessionStatus : {}", session.isConnected());
        session.disconnect(headers);
        log.info("disconnect 호출 - sessionStatus : {}", session.isConnected());
        log.error("StompHeaders : [{}], Payload : [{}]", headers, new String(payload));
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return ChatResponseDto.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        ChatPostDto msg = (ChatPostDto) payload;
        log.info("Received Message : [{}]", msg);
    }
    
    public synchronized void subscribe(String destination) {
        session.subscribe(destination, this);
        log.info("[{}] Subscribed.", destination);
    }
}
