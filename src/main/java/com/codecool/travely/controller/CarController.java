package com.codecool.travely.controller;

import com.codecool.travely.model.Car;
import com.codecool.travely.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CarController {

    private final CarService carService;

    @GetMapping("/all")
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.findAll());
    }

    @GetMapping("/filter-by-location/{location}")
    public ResponseEntity<List<Car>> filterByLocation(@PathVariable String location) {
        return ResponseEntity.ok(carService.findAllByLocation(location));
    }

    @GetMapping("/image/{id}/download")
    public byte[] downloadImage(@PathVariable Long id) {
        return carService.downloadImage(id);
    }
}
