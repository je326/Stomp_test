package com.example.chattest.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatApiResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    //성공 응답
    public ChatApiResponse(List<T> data) {
        this.statusCode = 200;
        this.data = data;
    }

    //에러 응답
    public ChatApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
