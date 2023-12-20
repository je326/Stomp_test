package com.example.chattest;

import com.example.chattest.domain.dto.StompRequest;
import com.example.chattest.domain.dto.StompResponse;
import com.example.chattest.service.ChatService;
import com.example.chattest.service.SessionEventListener;
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
//    private final ChatService chatService;
//    private final SessionEventListener sessionEventListener;

    //사용자 입장
    @MessageMapping("/chat/ENTER/{algorithmId}")
    public void enter(@DestinationVariable("algorithmId") Long algorithmId) {
        StompResponse response = new StompResponse();
        response.setContent("님이 입장하셨습니다.");
        simpMessagingTemplate.convertAndSend("/topic/chat/" + algorithmId, response);

//        log.info("Enter member: {} to chatting room: {}", request.getSender(), algorithmId);
    }

    //사용자 채팅
    @MessageMapping("/chat/{algorithmId}")
    public void chat(@DestinationVariable("algorithmId") Long algorithmId, StompRequest request) {
        StompResponse response = new StompResponse();
        response.setContent(request.getContent());
        simpMessagingTemplate.convertAndSend("/topic/chat/" + algorithmId, response);
        //채팅 DB에 저장
//        chatService.saveChat(algorithmId, response);

//        log.info("Message {} Enter member: {} to chatting room: {}", request.getContents(), request.getSender(), algorithmId);
    }

    //사용자 퇴장
    @MessageMapping("/chat/EXIT/{algorithmId}")
    public void exit(@DestinationVariable("algorithmId") Long algorithmId) {
        StompResponse response = new StompResponse();
        response.setContent("님이 퇴장하셨습니다.");
        simpMessagingTemplate.convertAndSend("/topic/chat/" + algorithmId, response);

//        log.info("Exit member: {} to chatting room: {}", request.getSender(), algorithmId);
    }
}
