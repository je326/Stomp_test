package com.example.chattest;

import com.example.chattest.domain.dto.StompNoticeResponse;
import com.example.chattest.domain.dto.StompRequest;
import com.example.chattest.domain.dto.StompResponse;
import com.example.chattest.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatStompController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserSessionRegistry userSessionRegistry;
    private final ChatService chatService;

    //사용자 입장 알림
    @MessageMapping("/chat/enter/{algorithmId}")
    public void enter(@DestinationVariable("algorithmId") Long algorithmId, StompHeaderAccessor accessor) {

        String sessionId = accessor.getSessionId();
        String nickname = userSessionRegistry.getNickname(sessionId);

        String content = nickname + " 님이 입장하셨습니다.";

        StompNoticeResponse noticeResponse = StompNoticeResponse.builder()
                .type("ENTER")
                .nickname(nickname)
                .content(content)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/chat/" + algorithmId, noticeResponse);

    }

    //사용자 채팅
    @MessageMapping("/chat/{algorithmId}")
    public void chat(@DestinationVariable("algorithmId") Long algorithmId, StompRequest request, StompHeaderAccessor accessor) {

        String sessionId = accessor.getSessionId();
        String nickname = userSessionRegistry.getNickname(sessionId);

        try {
            StompResponse response = chatService.saveAndMessage(algorithmId, nickname, request.getContent());
            simpMessagingTemplate.convertAndSend("/topic/chat/" + algorithmId, response);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
    }

    //사용자 퇴장 알림
    @MessageMapping("/chat/exit/{algorithmId}")
    public void exit(@DestinationVariable("algorithmId") Long algorithmId, StompHeaderAccessor accessor) {

        String sessionId = accessor.getSessionId();
        String nickname = userSessionRegistry.getNickname(sessionId);

        String content = nickname + " 님이 퇴장하셨습니다.";

        StompNoticeResponse noticeResponse = StompNoticeResponse.builder()
                .type("EXIT")
                .nickname(nickname)
                .content(content)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/chat/" + algorithmId, noticeResponse);

        userSessionRegistry.removeUser(sessionId);
    }

}

