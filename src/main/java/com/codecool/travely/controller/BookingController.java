package com.codecool.travely.controller;

import com.codecool.travely.model.Booking;
import com.codecool.travely.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('HOST') or hasRole('CUSTOMER')")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final JavaMailSender mailSender;

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Booking>> findAllByCustomerId(@PathVariable Long id) {
        return new ResponseEntity<>(bookingService.findAllByCustomerId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok("Booking has been deleted.");
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add-booking/host/{hostId}/customer/{customerId}/accommodation/{accommodationId}")
    public ResponseEntity<String> addBooking(@Valid @RequestBody Booking booking, @PathVariable Long hostId, @PathVariable Long customerId, @PathVariable Long accommodationId) {
        bookingService.saveBooking(booking, hostId, customerId, accommodationId);
//        mailSender.send(bookingService.createBookingMail(accommodationId, customerId)); # Todo activate mail sending
        return ResponseEntity.ok("Booking has been saved.");
    }

    @GetMapping("/by-accommodation/{id}")
    public ResponseEntity<Booking> getByAccommodationId(@PathVariable Long id) {
        return new ResponseEntity<>(bookingService.findByAccommodationId(id), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('HOST')")
//    @DeleteMapping("/decline-booking/{id}")
//    public ResponseEntity<String> declineBooking(@PathVariable Long id) {
//        bookingService.declineBooking(id);
//        return ResponseEntity.ok("Booking declined!");
//    }

}
