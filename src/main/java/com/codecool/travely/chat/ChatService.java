package com.codecool.travely.chat;

import com.codecool.travely.repository.ChatMessageRepository;
import com.codecool.travely.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final CustomerService customerService;

    public ChatMessage findById(Long id) {
        return chatMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find chat message with id: " + id));
    }

    public void simpleDbSave(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public void save(ChatMessage chatMessage) {
        chatMessage.setSender(customerService.findById(chatMessage.getMessageSenderId()));
        chatMessage.setReceiver(customerService.findById(chatMessage.getMessageReceiverId()));
        chatMessage.setTime(LocalDateTime.now());
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

    public List<List<ChatMessage>> getUnseenMessagesPerUser(Long id) {
        log.info("Fetching all unseen messages for each conversation that user with id " + id + "had.");
        List<List<ChatMessage>> chatMessages = new ArrayList<>();
        customerService.getAllCustomersExcept(id).forEach(customer -> {
            chatMessages.add(getAllForConversation(id, customer.getId()).stream().filter(chatMessage -> chatMessage.getType() == MessageType.SENT && (long) chatMessage.getSender().getId() == customer.getId()).collect(Collectors.toList()));
        });
        return chatMessages;
    }

    public void markMessageAsSeen(Long id) {
        log.info("Marking chat message with id " + id + " as seen.");
        ChatMessage chatMessage = findById(id);
        chatMessage.setType(MessageType.SEEN);
        simpleDbSave(chatMessage);
    }
}
