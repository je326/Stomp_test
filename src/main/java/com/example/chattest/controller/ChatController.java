package com.example.chattest.controller;

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
    public ResponseEntity<?> getChatsByAlgorithmId(@PathVariable("algorithmId") Long algorithmId) {
        try {
            List<ChatResponse> chats = chatService.getChatsByAlgorithmId(algorithmId);
            return ResponseEntity.ok().body(Map.of(
                    "statusCode", 200,
                    "data", chats
            ));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "statusCode", 4040,
                    "message", "채팅 내역을 찾을 수 없습니다."
            ));
        } catch (HttpMessageNotReadableException | MethodArgumentTypeMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "statusCode", 4006,
                    "message", "요청이 잘못 되었습니다."
            ));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "statusCode", 4036,
                    "message", "요청이 서버에서 거부되었습니다."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "statusCode", 5006,
                    "message", "서버에서 처리 중 오류가 발생했습니다."
            ));
        }
    }

    @GetMapping("/chat/search/{algorithmId}")
    public ResponseEntity<?> getChatsByKeyword(@PathVariable("algorithmId") Long algorithmId, @RequestParam("keyword") String keyword) {
        try {
            List<ChatResponse> chats = chatService.getChatsByKeyword(algorithmId, keyword);
            return ResponseEntity.ok().body(Map.of(
                    "statusCode", 200,
                    "data", chats
            ));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "statusCode", 4040,
                    "message", "해당 검색어을 찾을 수 없습니다."
            ));
        } catch (HttpMessageNotReadableException | MethodArgumentTypeMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "statusCode", 4006,
                    "message", "요청이 잘못 되었습니다."
            ));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "statusCode", 4036,
                    "message", "요청이 서버에서 거부되었습니다."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "statusCode", 5006,
                    "message", "서버에서 처리 중 오류가 발생했습니다."
            ));
        }
    }
}
