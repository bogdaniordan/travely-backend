package com.codecool.travely.chat;

import com.codecool.travely.repository.ChatMessageRepository;
import com.codecool.travely.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ChatService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final CustomerService customerService;

    public void save(ChatMessage chatMessage) {
        chatMessage.setSender(customerService.findById(chatMessage.getMessageSenderId()));
        chatMessage.setReceiver(customerService.findById(chatMessage.getMessageReceiverId()));
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getAllForConversation(long senderId, long receiverId) {
        log.info("Getting all messages between user with id " + senderId + " and " + receiverId);
        List<ChatMessage> messages = chatMessageRepository.findAll().stream()
                .filter(chatMessage -> chatMessage.getSender().getId() == senderId && chatMessage.getReceiver().getId() == receiverId)
                .collect(Collectors.toList());
        messages.addAll(chatMessageRepository.findAll().stream().filter(chatMessage -> chatMessage.getSender().getId() == receiverId && chatMessage.getReceiver().getId() == senderId).collect(Collectors.toList()));
        messages.sort(Comparator.comparing(ChatMessage::getTime));
        return messages;
    }

    public void notifyUser(final String id, final ChatMessage chatMessage) {
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", chatMessage);
    }
}
