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

    public Accommodation(String title, String address, String city, Date checkInDate, Date checkoutDate, int pricePerNight, List<Facility> facilities) {
        this.title = title;
        this.address = address;
        this.city = city;
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
        this.pricePerNight = pricePerNight;
        this.facilities = facilities;
    }
}
