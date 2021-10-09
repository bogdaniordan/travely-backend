package com.codecool.travely.model.booking;

import com.codecool.travely.model.Car;
import com.codecool.travely.model.user.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class CarBooking {
    @Id
    @GeneratedValue
    private Long id;

    private int price;

    private LocalDate startDate;

    private LocalDate endDate;

    private String notes;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Car car;

    private int childSeat;

    private boolean gps;

    private int babySeat;

    public CarBooking(int price, LocalDate startDate, LocalDate endDate, String notes, Customer customer, Car car) {
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
        this.customer = customer;
        this.car = car;
    }
}
