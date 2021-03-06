package com.codecool.travely.controller;

import com.codecool.travely.dto.request.BookingDatesDto;
import com.codecool.travely.model.booking.Booking;
import com.codecool.travely.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('HOST') or hasRole('CUSTOMER')")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

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
    public ResponseEntity<Booking> addBooking(@Valid @RequestBody Booking booking, @PathVariable Long hostId, @PathVariable Long customerId, @PathVariable Long accommodationId) {
        return ResponseEntity.ok(bookingService.saveBooking(booking, hostId, customerId, accommodationId));
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping("/all-bookings/{accommodationId}")
    public ResponseEntity<List<Booking>> getAllByAccommodation(@PathVariable Long accommodationId) {
        return ResponseEntity.ok(bookingService.findAllByAccommodation(accommodationId));
    }

    @GetMapping("/accommodation-is-booked-now/{id}")
    public ResponseEntity<Boolean> accommodationIsBookedNow(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.accommodationIsBookedNow(id));
    }

    @GetMapping("/accommodation-has-future-bookings/{accommodationId}")
    public ResponseEntity<Boolean> hasFutureBookings(@PathVariable Long accommodationId) {
        return ResponseEntity.ok(bookingService.getFutureBookings(accommodationId).size() > 0);
    }

    @GetMapping("/closest-future-booking/{id}")
    public ResponseEntity<Booking> getClosestFutureBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getClosestFutureBooking(id));
    }

    @GetMapping("/booked-dates/{id}")
    public ResponseEntity<List<LocalDate>> getAccommodationBookedDates(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getAccommodationBookedDates(id));
    }

    @GetMapping("/bookings-by-host/{id}")
    public ResponseEntity<List<Booking>> getAllByHost(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getAllForHost(id));
    }

    @GetMapping("/mark-as-seen/{id}")
    public ResponseEntity<String> markAsSeen(@PathVariable Long id) {
        bookingService.markBookingAsSeen(id);
        return ResponseEntity.ok("Booking marked as seen");
    }

    @GetMapping("/booked-nights-number/{userId}")
    public ResponseEntity<Integer> getNumberOfBookedNights(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getNumberOfBookedNights(userId));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @PutMapping("/update-booking-dates/{id}")
    public ResponseEntity<String> updateBookingDates(@RequestBody BookingDatesDto bookingDatesDto, @PathVariable Long id) {
        bookingService.updateBookingDates(id, bookingDatesDto);
        return ResponseEntity.ok("Booking dates have been updated.");
    }
}
