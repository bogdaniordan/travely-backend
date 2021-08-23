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

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find booking with id: " + id));
    }

    public void saveBooking(Booking booking) {
        log.info("Saving a new booking.");
        bookingRepository.save(booking);
    }
}
