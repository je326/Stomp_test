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

    public void printSessions() {
        for (String key : userSessions.keySet()) {
            String value = userSessions.get(key).toString();
            System.out.println(key + ":" + value);
        }
    }
}
