package com.codecool.travely.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "booking")
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Date checkInDate;
    @NotNull
    private Date checkoutDate;

    @ManyToOne
    private Host host;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Accommodation accommodation;

    public Booking(Date checkInDate, Date checkoutDate, Accommodation accommodation) {
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
        this.accommodation = accommodation;
    }
}
