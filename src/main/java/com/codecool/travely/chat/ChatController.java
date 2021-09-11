package com.codecool.travely.chat;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@PreAuthorize("hasRole('CUSTOMER')")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/send-message")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatService.save(chatMessage);
        return chatMessage;
    }

    @GetMapping("/all-conversation/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessage>> getAllMessagesBetween(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return ResponseEntity.ok(chatService.getAllForConversation(senderId, receiverId));
    }
}
