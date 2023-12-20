package com.example.chattest.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiExceptionEntity {
    private String statusCode;
    private String message;
}
