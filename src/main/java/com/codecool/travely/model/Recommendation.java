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
//    private boolean seen;
    @ManyToOne
    private Accommodation accommodation;
    @ManyToOne
    private Customer sender;
    @ManyToOne
    private Customer receiver;
}
