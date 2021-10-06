package com.codecool.travely.model;

import com.codecool.travely.enums.CarFacility;
import com.codecool.travely.enums.CarGear;
import com.codecool.travely.enums.FuelPolicy;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue
    private Long id;

    private int pricePerDay;

    private String model;

    private int seats;

    @Enumerated(EnumType.STRING)
    private CarGear carGear;

    private String location;

    private int mileage;

    @Enumerated(EnumType.STRING)
    private FuelPolicy fuelPolicy;

    private boolean fullInsurance;

//    @ElementCollection
//    private Set<CarFacility> facilities;

    private double rating;

    public Car(int pricePerDay, String model, int seats, CarGear carGear, String location, int mileage, boolean fullInsurance, FuelPolicy fuelPolicy) {
        this.pricePerDay = pricePerDay;
        this.model = model;
        this.seats = seats;
        this.carGear = carGear;
        this.location = location;
        this.mileage = mileage;
        this.fullInsurance = fullInsurance;
//        this.facilities = facilities;
        this.fuelPolicy = fuelPolicy;
    }
}
