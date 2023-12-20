package com.example.chattest.repository;

import com.example.chattest.domain.entity.Algorithm;
import com.example.chattest.domain.entity.Chat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class ChatRepositoryTest {

    @Autowired private ChatRepository chatRepository;

    @Autowired private AlgorithmRepository algorithmRepository;

    @Test
    void saveChat() {

//        Algorithm algorithm = new Algorithm(3L, "알송달송 문제");
//        Algorithm savedAlgorithm = algorithmRepository.save(algorithm);
        Optional<Algorithm> optionalAlgorithm = algorithmRepository.findById(4L);

        if (optionalAlgorithm.isPresent()) {
            Algorithm algorithm = optionalAlgorithm.get();

            Chat chat = Chat.builder()
                    .algorithm(algorithm)
                    .nickname("user3")
                    .content("안녕하세요.")
                    .build();

            Chat savedChat = chatRepository.save(chat);
            Assertions.assertThat(chat.getContent()).isEqualTo(savedChat.getContent());
        }
    }
}