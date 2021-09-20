package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity(name = "testimonial")
@Data
@NoArgsConstructor
public class Testimonial {
    @Id
    @GeneratedValue
    private Long id;
    private String message;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accommodation accommodation;

    @Min(0)
    @Max(5)
    private int rating;

    @ManyToOne
    private Customer customer;

    public Testimonial(String message, Accommodation accommodation, Customer customer, int rating) {
        this.message = message;
        this.accommodation = accommodation;
        this.customer = customer;
        this.rating = rating;
    }
}
