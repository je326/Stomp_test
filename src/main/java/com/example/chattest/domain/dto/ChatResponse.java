package com.example.chattest.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatResponse {

    private Long chatId;

    private Long algorithmId;

    private String nickname;

    private String content;

    private LocalDateTime createdAt;
}
