package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String location;
    private int pricePerNight;
    private AccommodationStatus status;
    private int capacity;
    @ElementCollection
    private List<Facility> facilities;

    public Accommodation(String title, String address, String location, int pricePerNight, List<Facility> facilities, AccommodationStatus status, int capacity) {
        this.title = title;
        this.address = address;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.facilities = facilities;
        this.status = status;
        this.capacity = capacity;
    }
}
