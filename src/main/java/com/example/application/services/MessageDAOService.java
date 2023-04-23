package com.example.application.services;

import com.example.application.entities.Message;
import com.example.application.enums.Status;
import com.example.application.repositories.MessageRepository;
import com.example.application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageDAOService {


    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

//    public Long countAll() {
//        return messageRepository.countAll();
//    }

    public ArrayList<Message> getAll() {
        return messageRepository.findAll();
    }

    public ArrayList<Message> getAllNewByChatId(Long chatId) {
        return messageRepository.findMessagesByChatIdAndStatus(chatId, Status.CREATED);
    }

//    public ArrayList<Message> getNewUserMessages(Long userId) {
//        return messageRepository.findMessagesByStatusAndUserId(Status.CREATED, userId);
//    }

    public boolean setMessageStatusSent(Long messageId) {
        var msg = messageRepository.findMessageById(messageId);
        msg.setStatus(Status.SENT);
        messageRepository.save(msg);
        return true;
    }

}
