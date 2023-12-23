package com.example.chattest;
import com.example.chattest.domain.dto.StompNoticeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebsocketEventListener {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserSessionRegistry userSessionRegistry;

    @EventListener
    public void handleSTOMPDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String[] split = accessor.getDestination().toString().split("/");
        String algorithmId = split[3];

        String sessionId = accessor.getSessionId();
        String nickname = userSessionRegistry.getNickname(sessionId);

        String message = nickname + " 님이 퇴장하셨습니다.";

        StompNoticeResponse noticeResponse = StompNoticeResponse.builder()
                .type("EXIT")
                .nickname(nickname)
                .content(message)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/chat/" + algorithmId, noticeResponse);

        userSessionRegistry.removeUser(sessionId);

    }
}

