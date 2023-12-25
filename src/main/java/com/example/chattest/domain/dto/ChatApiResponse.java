package com.example.chattest.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatApiResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    //성공 응답
    public ChatApiResponse(List<T> data) {
        this.status = 200;
        this.data = data;
    }

    //에러 응답
    public ChatApiResponse(int statusCode, String error) {
        this.status = statusCode;
        this.error = error;
    }
}
