package com.codecool.travely.chat;

import com.codecool.travely.repository.ChatMessageRepository;
import com.codecool.travely.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final CustomerService customerService;

    public void save(ChatMessage chatMessage) {
        chatMessage.setSender(customerService.findById(chatMessage.getMessageSenderId()));
        chatMessage.setReceiver(customerService.findById(chatMessage.getMessageReceiverId()));
        chatMessage.setTime(LocalDateTime.now().toString());
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getAllForConversation(long senderId, long receiverId) {
        log.info("Getting all messages between user with id " + senderId + " and " + receiverId);
        return chatMessageRepository.findAll().stream()
                .filter(chatMessage -> chatMessage.getSender().getId() == senderId && chatMessage.getReceiver().getId() == receiverId)
                .collect(Collectors.toList());
    }
}
