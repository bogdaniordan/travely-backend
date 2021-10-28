package com.codecool.travely.model;

import com.codecool.travely.enums.CleaningStatus;
import com.codecool.travely.enums.Facility;
import com.codecool.travely.enums.PlaceType;
import com.codecool.travely.model.user.Host;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity(name = "accommodation")
@Data
@NoArgsConstructor
public class Accommodation {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String title;

    @NotNull
    @Size(min = 5)
    private String address;

    @NotNull
    @Size(min = 3)
    private String location;

    @NotNull
    @Min(1)
    private int pricePerNight;

    @Enumerated(EnumType.STRING)
    private CleaningStatus cleaningStatus;

    @ElementCollection
    private Set<Facility> facilities;

    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    @ManyToOne
    private Host host;

    public Accommodation(String title, String address, String location, int pricePerNight, Set<Facility> facilities, PlaceType placeType, CleaningStatus cleaningStatus) {
        this.title = title;
        this.address = address;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.facilities = facilities;
        this.placeType = placeType;
        this.cleaningStatus = cleaningStatus;
    }
}
