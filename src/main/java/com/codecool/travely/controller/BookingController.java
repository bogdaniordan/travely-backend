package com.codecool.travely.controller;

import com.codecool.travely.dto.response.MessageResponse;
import com.codecool.travely.model.Booking;
import com.codecool.travely.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasAnyRole('CUSTOMER')")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/add-booking")
    public ResponseEntity<MessageResponse> addBooking(@RequestBody Booking booking) {
        bookingService.saveBooking(booking);
        return new ResponseEntity<>(new MessageResponse("Booking added."), HttpStatus.CREATED);
    }
}
