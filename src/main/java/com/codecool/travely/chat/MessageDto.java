package com.codecool.travely.chat;

import com.codecool.travely.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto {

    private String content;
    private Customer sender;
    private Customer receiver;
}
