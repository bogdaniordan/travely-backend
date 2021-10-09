package com.codecool.travely.controller;

import com.codecool.travely.dto.request.BookingDatesDto;
import com.codecool.travely.model.Car;
import com.codecool.travely.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/all")
    public ResponseEntity<List<Car>> getAllCars(@RequestBody BookingDatesDto bookingDatesDto) {
        return ResponseEntity.ok(carService.searchAllCars(bookingDatesDto));
    }

    @PostMapping("/filter-by-location/{location}")
    public ResponseEntity<List<Car>> filterByLocation(@PathVariable String location, @RequestBody BookingDatesDto bookingDatesDto) {
        return ResponseEntity.ok(carService.findAllByLocation(location, bookingDatesDto));
    }

    @GetMapping("/image/{id}/download")
    public byte[] downloadImage(@PathVariable Long id) {
        return carService.downloadImage(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.findById(id));
    }
}
