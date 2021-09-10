package com.codecool.travely.chat;

import com.codecool.travely.enums.MessageStatus;
import com.codecool.travely.model.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue
    private Long id;
    private String content;

    private String time;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @ManyToOne
    private Customer sender;
    @ManyToOne
    private Customer receiver;

}
