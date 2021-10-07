package com.codecool.travely.controller;

import com.codecool.travely.dto.request.BookingDatesDto;
import com.codecool.travely.service.CarBookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car-bookings")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('CUSTOMER')")
@AllArgsConstructor
public class CarBookingController {

    private final CarBookingService carBookingService;

    @PostMapping("/can-be-booked/{carId}")
    public ResponseEntity<Boolean> canBeBooked(@PathVariable Long carId, @RequestBody BookingDatesDto bookingDatesDto) {
        return ResponseEntity.ok(carBookingService.canBeBooked(bookingDatesDto, carId));
    }
}
