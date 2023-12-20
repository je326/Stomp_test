package com.example.chattest.service;

import com.example.chattest.domain.dto.ChatResponse;
import com.example.chattest.domain.dto.StompResponse;
import com.example.chattest.domain.entity.Algorithm;
import com.example.chattest.domain.entity.Chat;
import com.example.chattest.repository.AlgorithmRepository;
import com.example.chattest.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final AlgorithmRepository algorithmRepository;

    /**
     * 채팅 저장
     * @return chatID
     */
    @Transactional
    public Long saveChat(Long algorithmId, StompResponse chatResponse) {

        Optional<Algorithm> optionalAlgorithm = algorithmRepository.findById(algorithmId);

        if (optionalAlgorithm.isEmpty()) {
            throw new RuntimeException("Algorithm not found with id: " + algorithmId);
        }

        Chat chat = Chat.builder()
                .algorithm(optionalAlgorithm.get())
                .nickname(chatResponse.getNickname())
                .content(chatResponse.getContent())
                .build();

        chat = chatRepository.save(chat);

        return chat.getChatId();
    }

    /**
     * 특정 AlgorithmId에 해당하는 모든 Chat 조회하기
     * @return List<ChatResponse>
     */
    @Transactional
    public List<ChatResponse> getChatsByAlgorithmId(Long AlgorithmId) {
        List<Chat> chats = chatRepository.findByAlgorithm_AlgorithmId(AlgorithmId);

        if (chats.isEmpty()) {
            throw new NoSuchElementException();
        }

        return chats.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 keyword를 포함한 content를 가진 모든 Chat 조회하기
     * @return List<ChatResponse>
     */
    public List<ChatResponse> getChatsByKeyword(Long algorithmId, String keyword) {
        List<Chat> chats = chatRepository.findByKeyword(algorithmId, keyword);


        if (chats.isEmpty()) {
            throw new NoSuchElementException();
        }

        return chats.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ChatResponse toDto(Chat chat) {

        return ChatResponse.builder()
            .chatId(chat.getChatId())
            .algorithmId(chat.getAlgorithm().getAlgorithmId())
            .nickname(chat.getNickname())
            .content(chat.getContent())
            .createdAt(chat.getCreatedAt())
            .build();
}

}
