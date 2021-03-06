package com.codecool.travely.chat;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/send-message")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) throws InterruptedException {
        Thread.sleep(500);
        chatService.save(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat/private-message")
    @SendToUser("/topic/private-messages")
    public ChatMessage getPrivateMessage(@Payload ChatMessage message) throws InterruptedException {
        chatService.save(message);
        Thread.sleep(500);
        return message;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/all-conversation/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessage>> getAllMessagesBetween(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return ResponseEntity.ok(chatService.getAllForConversation(senderId, receiverId));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/unseen-messages/{id}")
    public ResponseEntity<List<List<ChatMessage>>> getUnseenMessagesPerUser(@PathVariable Long id) {
        return ResponseEntity.ok(chatService.getUnseenMessagesPerUser(id));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/mark-message-as-seen/{id}")
    public ResponseEntity<String> markMessageAsSeen(@PathVariable Long id) {
        chatService.markMessageAsSeen(id);
        return ResponseEntity.ok("Marked message as seen.");
    }
}
