package com.codecool.travely.service;

import com.codecool.travely.dto.request.BookingDatesDto;
import com.codecool.travely.enums.AccommodationStatus;
import com.codecool.travely.model.Accommodation;
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

    public void saveBooking(Booking booking, Long hostId, Long customerId, Long accommodationId) {
        booking.setHost(hostService.findById(hostId));
        Accommodation accommodation = accommodationService.findById(accommodationId);
        accommodation.setStatus(AccommodationStatus.Booked);
        accommodationService.saveAccommodation(accommodation);
        booking.setAccommodation(accommodation);
        booking.setCustomer(customerService.findById(customerId));
        bookingRepository.save(booking);
    }

    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    public List<Booking> findAllByCustomerId(Long id) {
        log.info("Fetching bookings for customer with id: " + id);
        return bookingRepository.findBookingsByCustomerId(id);
    }

    public void cancelBooking(Long id) {
        log.info("Deleting booking with id: " + id);
        Booking booking = findById(id);
        Accommodation accommodation = booking.getAccommodation();
        accommodation.setStatus(AccommodationStatus.Free);
        accommodationService.saveAccommodation(accommodation);
        bookingRepository.delete(booking);
    }

//  #Todo booking mail

//    public SimpleMailMessage createBookingMail(Long accommodationId, Long customerId) {
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setSubject("Booking for " + accommodationService.findById(accommodationId).getTitle());
//        email.setText("Congrats " + customerService.findById(customerId).getFirstName() + " " + customerService.findById(customerId).getLastName() + " , your booking has been made. Please access the following link in order to check your bookings: http://localhost:3000/profile");
//        email.setTo(customerService.findById(customerId).getEmail());
//        email.setFrom("noreply@travely");
//        return email;
//    }

    public boolean accommodationCanBeBooked(BookingDatesDto bookingDates, long accommodationId) {
        log.info("Checking if accommodation with id " + accommodationId + " can be booked between " + bookingDates.getCheckOut() + " and " + bookingDates.getCheckOut());
        for (Booking booking: bookingRepository.findAllByAccommodationId(accommodationId)) {
            if (bookingDates.getCheckIn().compareTo(booking.getCheckInDate()) >= 0 && bookingDates.getCheckIn().compareTo(booking.getCheckoutDate()) <= 0
                    || bookingDates.getCheckOut().compareTo(booking.getCheckInDate()) >= 0 && bookingDates.getCheckOut().compareTo(booking.getCheckoutDate()) <= 0) {
                return false;
            }
        }
        return true;
    }

    public List<Booking> findAllByAccommodation(Long id) {
        log.info("Fetching all bookings for accommodation with id " + id);
        return bookingRepository.findAllByAccommodationId(id);
    }


}
