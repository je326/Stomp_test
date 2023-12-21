package com.example.chattest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompChannelInterceptor stompChannelInterceptor;

    /**
     * 웹소켓 연결 메서드
     * 클라이언트가 ws://[서버주소]/ws/chat 으로 웹소켓 연결을 시도
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("http://localhost:8080")
                .withSockJS();

        registry.addEndpoint("/ws/chat/test").setAllowedOriginPatterns("http://localhost:8080");
    }

    /**
     * STOMP 사용을 위한 내장 Message Broker 설정 메서드
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); //클라이언트가 메시지를 보낼 때 경로 맨앞에 "/chat"이 붙어있으면 Message Broker 로 보냄
        registry.enableSimpleBroker("/topic"); //Message Broker 가 해당 채팅방을 구독하고 있는 클라이언트에게 메시지를 전달, "/topic"은 1대다 메시징
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompChannelInterceptor);
    }
}
