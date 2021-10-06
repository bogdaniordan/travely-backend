package com.codecool.travely.model;

import com.codecool.travely.enums.CleaningStatus;
import com.codecool.travely.enums.Facility;
import com.codecool.travely.enums.PlaceType;
import com.codecool.travely.model.user.Host;
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

    @Enumerated(EnumType.STRING)
    private CleaningStatus cleaningStatus;

    @ElementCollection
    private List<Facility> facilities;

    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    @ManyToOne
    private Host host;

    public Accommodation(String title, String address, String location, int pricePerNight, List<Facility> facilities, PlaceType placeType, CleaningStatus cleaningStatus) {
        this.title = title;
        this.address = address;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.facilities = facilities;
        this.placeType = placeType;
        this.cleaningStatus = cleaningStatus;
    }
}
