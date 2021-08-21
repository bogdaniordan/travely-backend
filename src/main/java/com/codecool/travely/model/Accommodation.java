package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity(name = "accommodation")
@Data
@NoArgsConstructor
public class Accommodation {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String address;
    private String city;
    private Date checkInDate;
    private Date checkoutDate;
    private int pricePerNight;
    @ElementCollection
    private List<Facility> facilities;
}
