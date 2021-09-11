package com.codecool.travely.chat;

import com.codecool.travely.enums.MessageType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {
    private String content;
    private String sender;
    private LocalDateTime timeStamp = LocalDateTime.now();

    private MessageType type;


}
