package com.codecool.travely.service;

import com.codecool.travely.model.Booking;
import com.codecool.travely.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final AccommodationService accommodationService;
    private final HostService hostService;
    private final CustomerService customerService;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find booking with id: " + id));
    }

    public Booking saveBooking(Booking booking, Long hostId, Long customerId, Long accommodationId) {
        log.info("Saving a new booking.");
        booking.setHost(hostService.findById(hostId));
        booking.setAccommodation(accommodationService.findById(accommodationId));
        booking.setCustomer(customerService.findById(customerId));
        return bookingRepository.save(booking);
    }
}
