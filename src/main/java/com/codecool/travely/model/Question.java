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
    private Boolean solved;
    private String text;
    private String author;
    private boolean seen;
    private String response;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Host host;

}
