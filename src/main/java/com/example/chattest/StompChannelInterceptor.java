package com.example.chattest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompChannelInterceptor implements ChannelInterceptor {

    private final UserSessionRegistry userSessionRegistry;

    /**
     * 웹 소켓이 연결될 때, 해당 사용자의 sessionId와 nickname 추출
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(accessor.getCommand() == StompCommand.CONNECT) {

            String nickname = accessor.getFirstNativeHeader("nickname");
            String sessionId = accessor.getSessionId();

            log.info("sessionId={}, nickname={}", sessionId, nickname);

            userSessionRegistry.register(sessionId, nickname);
        }
        return message;
    }
}