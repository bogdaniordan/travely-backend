package com.codecool.travely.service;

import com.codecool.travely.dto.request.BookingDatesDto;
import com.codecool.travely.exception.customs.AccommodationIdNotFound;
import com.codecool.travely.model.Accommodation;
import com.codecool.travely.model.booking.Booking;
import com.codecool.travely.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        booking.setHost(hostService.findById(hostId));
        Accommodation accommodation = accommodationService.findById(accommodationId);
        booking.setAccommodation(accommodation);
        booking.setCustomer(customerService.findById(customerId));
        return bookingRepository.save(booking);
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
        bookingRepository.delete(booking);
    }

//      #Todo booking mail
//    public SimpleMailMessage createBookingMail(Long accommodationId, Long customerId) {
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setSubject("Booking for " + accommodationService.findById(accommodationId).getTitle());
//        email.setText("Congrats " + customerService.findById(customerId).getFirstName() + " " + customerService.findById(customerId).getLastName() + " , your booking has been made. Please access the following link in order to check your bookings: http://localhost:3000/profile");
//        email.setTo(customerService.findById(customerId).getEmail());
//        email.setFrom("noreply@travely");
//        return email;
//    }


    public List<Booking> findAllByAccommodation(Long id) {
        log.info("Fetching all bookings for accommodation with id " + id);
        if(!accommodationService.findAll().stream().map(Accommodation::getId).collect(Collectors.toList()).contains(id)) {
            throw new AccommodationIdNotFound("Could not find accommodation with id " + id);
        }
        return bookingRepository.findAllByAccommodationId(id);
    }

    public Boolean accommodationIsBookedNow(Long accommodationId) {
        log.info("Checking if accommodation with id " + accommodationId + " is booked at the moment.");
        for(Booking booking: bookingRepository.findAllByAccommodationId(accommodationId)) {
            if (LocalDate.now().compareTo(booking.getCheckInDate()) >= 0 && LocalDate.now().compareTo(booking.getCheckoutDate()) <= 0) {
                return true;
            }
        }
        return false;
    }

    public List<Booking> getFutureBookings(Long accommodationId) {
        log.info("Fetching future bookings for accommodation with id " + accommodationId);
        List<Booking> futureBookings = new ArrayList<>();
        bookingRepository.findAllByAccommodationId(accommodationId).forEach(booking -> {
            if (booking.getCheckInDate().compareTo(LocalDate.now()) > 0) {
                futureBookings.add(booking);
            }
        });
        return futureBookings;
    }

    public Booking getClosestFutureBooking(Long accommodationId) {
        List<Booking> futureBookings = getFutureBookings(accommodationId);
        log.info("Fetching closest future booking for accommodation with id " + accommodationId);
        if (futureBookings.size() == 1) {
            return futureBookings.get(0);
        }
        futureBookings.sort(Comparator.comparing(Booking::getCheckInDate).reversed());
        System.out.println(futureBookings.get(0));
        return futureBookings.get(0);
    }

    public List<LocalDate> getAccommodationBookedDates(Long id) {
        log.info("Fetching all booked dates for an accommodation");
        return getBookedDates(findAllByAccommodation(id));
    }

    public List<LocalDate> getBookedDates(List<Booking> bookings) {
        List<LocalDate> dates = new ArrayList<>();
        bookings.forEach(booking -> {
            LocalDate start = booking.getCheckInDate();
            LocalDate end = booking.getCheckoutDate();
            while(!start.isAfter(end)) {
                dates.add(start);
                start = start.plusDays(1);
            }
        });
        return dates;
    }

    public List<Booking> getAllForHost(Long id) {
        log.info("Fetching all bookings for host with id " + id);
        return findAll().stream().filter(booking -> booking.getHost().getId() == (long) id).collect(Collectors.toList());
    }

    public void markBookingAsSeen(Long id) {
        log.info("Marking booking with id " + id + " as seen.");
        Booking booking = findById(id);
        booking.setSeen(true);
        save(booking);
    }

    public int getNumberOfBookedNights(Long id) {
        log.info("Fetching number of booked nights for user with id " + id);
        return getBookedDates(findAllByCustomerId(id)).size();
    }

    public void updateBookingDates(Long bookingId, BookingDatesDto bookingDatesDto) {
        log.info("Updating booking dates for booking with id " + bookingId);
        Booking booking = findById(bookingId);
        booking.setCheckInDate(bookingDatesDto.getCheckIn());
        booking.setCheckoutDate(bookingDatesDto.getCheckOut());
        booking.setRescheduled(true);
        save(booking);
    }
}
