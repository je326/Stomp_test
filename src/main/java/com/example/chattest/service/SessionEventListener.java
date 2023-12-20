package com.example.chattest.service;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

@Component
public class SessionEventListener implements ApplicationListener<SessionConnectedEvent> {

    private final SubProtocolWebSocketHandler subProtocolWebSocketHandler;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public SessionEventListener(WebSocketHandler webSocketHandler, SimpMessageSendingOperations simpMessageSendingOperations) {
        this.subProtocolWebSocketHandler = (SubProtocolWebSocketHandler) webSocketHandler;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
    }

    public int getCurrentSessionCount() {
        return subProtocolWebSocketHandler.getStats().getWebSocketSessions();
    }
}