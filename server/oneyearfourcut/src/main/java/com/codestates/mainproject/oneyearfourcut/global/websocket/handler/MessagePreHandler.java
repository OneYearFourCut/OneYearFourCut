package com.codestates.mainproject.oneyearfourcut.global.websocket.handler;

import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
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
import org.springframework.validation.ObjectError;

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
        boolean isVerify = command.equals("CONNECT") || command.equals("SEND") || command.equals("SUBSCRIBE");

        Long senderId = null;
        Jws<Claims> claims = null;
        switch (command) { // 테스트용 command 로그
            case ("CONNECT"):
                log.info("CONNECT 요청");
                break;
            case ("DISCONNECT"):
                log.info("DISCONNECT 요청");
                break;
            case ("SUBSCRIBE"):
                log.info("SUBSCRIBE 요청");
                break;
            case ("UNSUBSCRIBE"):
                log.info("UNSUBSCRIBE 요청");
                break;
            case ("SEND"):
                log.info("SEND 요청");
                break;
            case ("ACK"):
                log.info("ACK 요청");
                break;
            case ("BEGIN"):
                log.info("BEGIN 요청");
                break;
            case ("COMMIT"):
                log.info("COMMIT 요청");
                break;
            case ("ABORT"):
                log.info("ABORT 요청");
                break;
        }
        // 연결, 메세지 발행, 구독일 때만 토큰 검사
        if (isVerify) {
            String authorizationHeader = String.valueOf(accessor.getFirstNativeHeader("Authorization"));
            try {
                log.info("authorizationHeader : {}", authorizationHeader);
                String jws = authorizationHeader.replace("Bearer ", "");
                log.info("jws : {}", jws);
                String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
                claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey);

            } catch (ExpiredJwtException ne) {
                log.info("EXPIRED_ACCESS_TOKEN");
                throw new BusinessLogicException(ExceptionCode.EXPIRED_ACCESS_TOKEN);
            } catch (SignatureException se) {
                log.info("WRONG_ACCESS_TOKEN");
                throw new BusinessLogicException(ExceptionCode.WRONG_ACCESS_TOKEN);
            } catch (NullPointerException ne) {
                log.info("NO_ACEESS_TOKEN");
                throw new BusinessLogicException(ExceptionCode.NO_ACEESS_TOKEN);
            }
            senderId = Long.valueOf((Integer) claims.getBody().get("id"));
            log.info("senderId : {}", senderId);
            message.getHeaders().put("senderId", senderId);
        }
        return message;
    }
}
