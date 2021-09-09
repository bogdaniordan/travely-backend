package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue
    private Long id;
    private String message;
    @ManyToOne
    private Accommodation accommodation;
    @ManyToOne
    private Customer sender;
    @ManyToOne
    private Customer receiver;

    public Recommendation(String message, Accommodation accommodation, Customer sender, Customer receiver) {
        this.message = message;
        this.accommodation = accommodation;
        this.sender = sender;
        this.receiver = receiver;
    }
}
