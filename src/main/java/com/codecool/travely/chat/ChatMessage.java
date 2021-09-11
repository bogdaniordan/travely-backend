package com.codecool.travely.chat;

import com.codecool.travely.model.Customer;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Data
public class ChatMessage {
    @Id
    @GeneratedValue
    private String content;
    private Long messageSenderId;
    private Long messageReceiverId;

    @ManyToOne
    private Customer sender;

    @ManyToOne
    private Customer receiver;

    private String time;

    private MessageType type;
}
