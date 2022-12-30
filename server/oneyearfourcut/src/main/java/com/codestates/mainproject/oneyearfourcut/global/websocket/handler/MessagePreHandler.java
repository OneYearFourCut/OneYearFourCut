package com.codestates.mainproject.oneyearfourcut.global.websocket.handler;

import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessagePreHandler implements ChannelInterceptor {

    private final JwtTokenizer jwtTokenizer;

    private final Gson gson;

    // 메시지를 처리하기전 실행해야 하는 메서드 -> 토큰 검사
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String command = String.valueOf(accessor.getCommand());

        // 일단 연결할 때만 jwt 검사
        if (command.equals("CONNECT")) {
            log.info("WebSocket CONNECT 요청");
            String authorizationHeader = String.valueOf(accessor.getNativeHeader("Authorization"));
//            try {
//                String jws = authorizationHeader.replace("Bearer ", "");
//                String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
//                Jws<Claims> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey);
//
//            } catch (ExpiredJwtException ne) {
//                throw new MessageDescriptorFormatException()
//            } catch (SignatureException se) {
//
//            } catch (NullPointerException ne) {
//
//            }
        }
        if (command.equals("SEND")) {
            log.info("WebSocket SEND 요청");
            log.info("WebSocket SEND 요청(payload) : {}", message.getPayload());
            log.info("WebSocket SEND 요청(header) : {}", message.getHeaders());
            log.info("WebSocket SEND 요청(payload) : {}", gson.toJson(message.getPayload()));
        }

        return message;
    }
}
