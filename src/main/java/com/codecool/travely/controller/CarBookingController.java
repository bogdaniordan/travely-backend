package com.codecool.travely.controller;

import com.codecool.travely.model.booking.CarBooking;
import com.codecool.travely.service.CarBookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car-bookings")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('CUSTOMER')")
@AllArgsConstructor
public class CarBookingController {

    private final CarBookingService carBookingService;

    @PostMapping("/save-booking/{customerId}/{carId}")
    public ResponseEntity<String> saveBooking(@PathVariable Long customerId, @PathVariable Long carId, @RequestBody CarBooking carBooking) {
        carBookingService.bookCar(carBooking, customerId, carId);
        return ResponseEntity.ok("Car booking has been saved.");
    }

    @GetMapping("/bookings-by-customer/{id}")
    public ResponseEntity<List<CarBooking>> getAllByCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(carBookingService.findAllByCustomer(id));
    }

    @DeleteMapping("/cancel-booking/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        carBookingService.cancelBooking(id);
        return ResponseEntity.ok("Booking with id " + id + " has been canceled.");
    }
}
