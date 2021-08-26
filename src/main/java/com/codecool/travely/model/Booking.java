package com.codecool.travely.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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

    @ManyToOne
    private Host host;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Accommodation accommodation;

    public Booking(LocalDate checkInDate, LocalDate checkoutDate, Accommodation accommodation) {
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
        this.accommodation = accommodation;
    }
}
