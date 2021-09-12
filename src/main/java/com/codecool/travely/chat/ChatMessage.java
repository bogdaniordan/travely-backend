package com.codecool.travely.chat;

import com.codecool.travely.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private Long messageSenderId;
    private Long messageReceiverId;

    @ManyToOne
    private Customer sender;

    @ManyToOne
    private Customer receiver;

    private LocalDateTime time = LocalDateTime.now();

    private MessageType type;

    public ChatMessage(String content, Customer sender, Customer receiver, MessageType type) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
    }
}
