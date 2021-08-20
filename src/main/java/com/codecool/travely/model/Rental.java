package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity(name = "rental")
@Data
@NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue
    private Long id;
    private String address;
    private Date checkInDate;
    private Date checkoutDate;
    private int pricePerNight;
    @ElementCollection
    private List<Facility> facilities;
}
