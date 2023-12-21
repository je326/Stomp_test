package com.example.chattest.service;

import com.example.chattest.domain.dto.ChatResponse;
import com.example.chattest.domain.dto.StompResponse;
import com.example.chattest.domain.entity.Algorithm;
import com.example.chattest.domain.entity.Chat;
import com.example.chattest.repository.AlgorithmRepository;
import com.example.chattest.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final AlgorithmRepository algorithmRepository;

    /**
     * Chat 저장
     */
    public StompResponse saveAndMessage(Long AlgorithmId, String nickname, String content) {

        Algorithm algorithm = algorithmRepository.findById(AlgorithmId)
                .orElseThrow(() -> new NoSuchElementException("해당 알고리즘 문제가 존재하지 않습니다."));

        Chat chat = Chat.builder()
                .nickname(nickname)
                .algorithm(algorithm)
                .content(content)
                .build();

        chat = chatRepository.save(chat);

        return StompResponse.builder()
                .type("MESSAGE")
                .chatId(chat.getChatId())
                .nickname(chat.getNickname())
                .content(chat.getContent())
                .createAt(Timestamp.valueOf(chat.getCreatedAt()))
                .build();

    }

    /**
     * 특정 AlgorithmId에 해당하는 모든 Chat 조회하기
     * @return List<ChatResponse>
     */
    public List<ChatResponse> getChats(Long algorithmId) {

        findAlgorithm(algorithmId);

        List<Chat> chats = chatRepository.findByAlgorithm_AlgorithmId(algorithmId);

        return chats.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 keyword를 포함한 content를 가진 모든 Chat 조회하기
     * @return List<ChatResponse>
     */
    public List<ChatResponse> searchChats(Long algorithmId, String keyword) {

        findAlgorithm(algorithmId);

        List<Chat> chats = chatRepository.findByKeyword(algorithmId, keyword);

        return chats.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 요청 파라미터가 유효한 데이터인지 검사
     */
    private void findAlgorithm(Long algorithmId) {
        Optional<Algorithm> algorithm = algorithmRepository.findById(algorithmId);
        if (algorithm.isEmpty()) {
            throw new NoSuchElementException("해당 알고리즘 문제가 존재하지 않습니다.");
        }
    }

    //Chat 엔티티를 ChatResponse 로 변환 (Entity -> DTO)
    public ChatResponse toDto(Chat chat){
        return ChatResponse.builder()
                .chatId(chat.getChatId())
                .algorithmId(chat.getAlgorithm().getAlgorithmId())
                .nickname(chat.getNickname())
                .content(chat.getContent())
                .createdAt(chat.getCreatedAt())
                .build();
    }
}