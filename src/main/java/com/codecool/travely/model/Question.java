package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
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
