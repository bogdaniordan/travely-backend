package com.codecool.travely.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "booking")
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkoutDate;

    private int price;

    @ManyToOne
    private Host host;

    @ManyToOne
    private Customer customer;

    private boolean seen = false;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accommodation accommodation;

    public Booking(LocalDate checkInDate, LocalDate checkoutDate, Accommodation accommodation, int price) {
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
        this.accommodation = accommodation;
        this.price = price;
    }
}
