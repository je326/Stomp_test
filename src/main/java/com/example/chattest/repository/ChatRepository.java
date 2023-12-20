package com.example.chattest.repository;

import com.example.chattest.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    //특정 AlgorithmId에 해당하는 Chat 반환
    List<Chat> findByAlgorithm_AlgorithmId(Long algorithmId);

    //특정 keyword를 포함하고 있는 content를 가진 Chat 반환
    @Query("SELECT c FROM Chat c WHERE c.algorithm.algorithmId = :algorithmId AND c.content like %:keyword%")
    List<Chat> findByKeyword(@Param("algorithmId") Long algorithmId, @Param("keyword")String keyword);
}
