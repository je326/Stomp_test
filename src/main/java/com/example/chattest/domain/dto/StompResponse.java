package com.example.chattest.domain.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StompResponse {

    private String type;

    private Long chatId;

    private String nickname;

    private String content;

    private Timestamp createAt;
}
