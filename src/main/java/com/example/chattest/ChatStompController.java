package com.example.chattest;

import com.example.chattest.domain.dto.StompRequest;
import com.example.chattest.domain.dto.StompResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatStompController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    //사용자 입장 알림
    @MessageMapping("/chat/enter/{algorithmId}")
    public void enter(@DestinationVariable("algorithmId") Long algorithmId) {
        StompResponse response = new StompResponse();
        response.setContent("님이 입장하셨습니다.");
        simpMessagingTemplate.convertAndSend("/topic/chat/" + algorithmId, response);

    }

    //사용자 채팅
    @MessageMapping("/chat/{algorithmId}")
    public void chat(@DestinationVariable("algorithmId") Long algorithmId, StompRequest request) {
        StompResponse response = new StompResponse();
        response.setContent(request.getContent());
        simpMessagingTemplate.convertAndSend("/topic/chat/" + algorithmId, response);
        //TODO - DB 저장 로직

    }

    //사용자 퇴장 알림
    @MessageMapping("/chat/exit/{algorithmId}")
    public void exit(@DestinationVariable("algorithmId") Long algorithmId) {
        StompResponse response = new StompResponse();
        response.setContent("님이 퇴장하셨습니다.");
        simpMessagingTemplate.convertAndSend("/topic/chat/" + algorithmId, response);

    }
}

