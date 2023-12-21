package com.example.chattest.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRequest {

    private Long algorithmId;

    private String nickname;

    private String content;
}
