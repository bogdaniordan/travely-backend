package com.codecool.travely.model;

import com.codecool.travely.enums.AccommodationStatus;
import com.codecool.travely.enums.Facility;
import com.codecool.travely.enums.PlaceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "accommodation")
@Data
@NoArgsConstructor
public class Accommodation {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String address;
    @NotNull
    private String location;
    @NotNull
    private int pricePerNight;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccommodationStatus status;
    @ElementCollection
    private List<Facility> facilities;
    @OneToOne
    private ImageUrls imageUrls;
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;
    @ManyToOne
    private Host host;

    public Accommodation(String title, String address, String location, int pricePerNight, List<Facility> facilities, AccommodationStatus status, PlaceType placeType) {
        this.title = title;
        this.address = address;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.facilities = facilities;
        this.status = status;
        this.placeType = placeType;
    }
}
