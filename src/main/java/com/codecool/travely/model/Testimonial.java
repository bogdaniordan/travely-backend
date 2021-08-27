package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "testimonial")
@Data
@NoArgsConstructor
public class Testimonial {
    @Id
    @GeneratedValue
    private Long id;
    private String message;

    @ManyToOne
    private Accommodation accommodation;

    @ManyToOne
    private Customer customer;

    public Testimonial(String message, Accommodation accommodation, Customer customer) {
        this.message = message;
        this.accommodation = accommodation;
        this.customer = customer;
    }
}
