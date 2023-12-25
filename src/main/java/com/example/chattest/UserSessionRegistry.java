package com.example.chattest;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserSessionRegistry {

    private final Map<String, String> userSessions = new ConcurrentHashMap<>();

    public void register(String sessionId, String nickname) {
        userSessions.put(sessionId, nickname);
    }

    public String getNickname(String sessionId) {
        return userSessions.get(sessionId);
    }

    public void removeUser(String sessionId) {
        userSessions.remove(sessionId);
    }

    public String getSessionId(String nickname) {
        for (Map.Entry<String, String> entry : userSessions.entrySet()) {
            if (entry.getValue().equals(nickname)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
