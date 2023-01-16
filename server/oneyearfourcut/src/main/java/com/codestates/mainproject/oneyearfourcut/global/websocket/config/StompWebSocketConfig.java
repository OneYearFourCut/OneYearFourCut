package com.codestates.mainproject.oneyearfourcut.global.websocket.config;

import com.codestates.mainproject.oneyearfourcut.global.websocket.handler.MessageErrorHandler;
import com.codestates.mainproject.oneyearfourcut.global.websocket.handler.MessagePreHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Configuration
@EnableScheduling
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final MessagePreHandler messagePreHandler;
    private final MessageErrorHandler messageErrorHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/stomp")  // websocket 핸드쉐이크 커넥션 엔드포인트
                .setAllowedOriginPatterns("*") // 테스트용, 실제 : front분들 요청의 origin으로 변경해야 함.
                .withSockJS().setDisconnectDelay(30 * 1000);
        registry.setErrorHandler(messageErrorHandler);

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); // 수신
        registry.setApplicationDestinationPrefixes("/pub"); // 발신 /pub/chat/message
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(messagePreHandler);
    }
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(160 * 64 * 1024);    // Max incoming message size, default : 64 * 1024
        registry.setSendTimeLimit(20 * 10000);            // default : 10 * 10000
        registry.setSendBufferSizeLimit(10 * 512 * 1024); // Max outgoing buffer size, default : 512 * 1024
    }
}
