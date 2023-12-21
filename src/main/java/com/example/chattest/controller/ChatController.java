package com.example.chattest.controller;

import com.example.chattest.domain.dto.ChatApiResponse;
import com.example.chattest.domain.dto.ChatResponse;
import com.example.chattest.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat/{algorithmId}")
    public ChatApiResponse<?> getChatsByAlgorithmId(@PathVariable("algorithmId") Long algorithmId) {

        try {
            List<ChatResponse> chats = chatService.getChats(algorithmId);
            return new ChatApiResponse<>(chats);
        } catch (NoSuchElementException e) {
            return new ChatApiResponse<>(4040, e.getMessage());
        } catch (HttpMessageNotReadableException | MethodArgumentTypeMismatchException e) {
            return new ChatApiResponse<>(4006, e.getMessage());
        } catch (AccessDeniedException e) {
            return new ChatApiResponse<>(4036, e.getMessage());
        } catch (Exception e) {
            return new ChatApiResponse<>(5006, e.getMessage());
        }
    }

    @GetMapping("/chat/search/{algorithmId}")
    public ChatApiResponse<?> getChatsByKeyword(@PathVariable("algorithmId") Long algorithmId, @RequestParam("keyword") String keyword) {

        try {
            List<ChatResponse> chats = chatService.searchChats(algorithmId, keyword);
            return new ChatApiResponse<>(chats);
        } catch (NoSuchElementException e) {
            return new ChatApiResponse<>(4040, e.getMessage());
        } catch (HttpMessageNotReadableException | MethodArgumentTypeMismatchException e) {
            return new ChatApiResponse<>(4006, e.getMessage());
        } catch (AccessDeniedException e) {
            return new ChatApiResponse<>(4036, e.getMessage());
        } catch (Exception e) {
            return new ChatApiResponse<>(5006, e.getMessage());
        }
    }
}
