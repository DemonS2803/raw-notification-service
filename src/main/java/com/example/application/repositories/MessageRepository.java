package com.example.application.repositories;

import com.example.application.entities.Message;
import com.example.application.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    ArrayList<Message> findAll();
    Message findMessageById(Long messageId);
    //    ArrayList<Message> findMessagesByStatusAndUserId(Status status, Long userId);
    ArrayList<Message> findMessagesByChatId(Long chatId);
    ArrayList<Message> findMessagesByChatIdAndStatus(Long chatId, Status status);
}
