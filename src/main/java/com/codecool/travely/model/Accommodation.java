package com.codecool.travely.model;

import com.codecool.travely.enums.AccommodationStatus;
import com.codecool.travely.enums.Facility;
import com.codecool.travely.enums.PlaceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @OneToOne
    private ImageUrls imageUrls;
    private PlaceType placeType;
    @ManyToOne
    private Host host;

    public Accommodation(String title, String address, String location, int pricePerNight, List<Facility> facilities, AccommodationStatus status, int capacity, PlaceType placeType) {
        this.title = title;
        this.address = address;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.facilities = facilities;
        this.status = status;
        this.capacity = capacity;
        this.placeType = placeType;
    }
}
