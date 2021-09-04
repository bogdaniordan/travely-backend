package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "question")
@Data
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private boolean solved;
    private String text;
    private String author;
    private boolean seen;
    private String response;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Host host;

    public Question(LocalDate date, String text, String author, Customer customer, Host host) {
        this.date = date;
        this.text = text;
        this.author = author;
        this.customer = customer;
        this.host = host;
    }
}
