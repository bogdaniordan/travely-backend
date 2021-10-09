package com.codecool.travely.service;

import com.codecool.travely.dto.request.BookingDatesDto;
import com.codecool.travely.model.Car;
import com.codecool.travely.model.booking.CarBooking;
import com.codecool.travely.repository.CarBookingRepository;
import com.codecool.travely.repository.CarRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CarBookingService {

    private final CarBookingRepository carBookingRepository;
    private final CustomerService customerService;
    private final CarRepository carRepository;

    public List<CarBooking> findAllByCar(Long id) {
        log.info("Fetching all car bookings for car with id " + id);
        return carBookingRepository.findAll().stream().filter(carBooking -> carBooking.getCar().getId() == (long) id).collect(Collectors.toList());
    }

    public boolean canBeBooked(BookingDatesDto bookingDates, long carId) {
        log.info("Checking if car with id " + carId + " can be booked between " + bookingDates.getCheckIn() + " and " + bookingDates.getCheckOut());
        for (CarBooking carBooking: findAllByCar(carId)) {
            if (bookingDates.getCheckIn().compareTo(carBooking.getStartDate()) >= 0 && bookingDates.getCheckIn().compareTo(carBooking.getEndDate()) <= 0
                    || bookingDates.getCheckOut().compareTo(carBooking.getStartDate()) >= 0 && bookingDates.getCheckOut().compareTo(carBooking.getEndDate()) <= 0) {
                return false;
            }
        }
        return true;
    }

    public void bookCar(CarBooking carBooking, Long customerId, Long carId) {
        log.info("Customer with id " + customerId + " is booking car with id " + carId);
        Car car = carRepository.findById(carId)
                        .orElseThrow(() -> new ResourceNotFoundException("Could not find car with id " + carId));
        carBooking.setCar(car);
        carBooking.setCustomer(customerService.findById(customerId));
        carBookingRepository.save(carBooking);
    }

    public List<CarBooking> findAllByCustomer(Long id) {
        log.info("Fetching all car bookings for customer with id " + id);
        return carBookingRepository.findAll().stream().filter(carBooking -> carBooking.getCustomer().getId() == (long) id).collect(Collectors.toList());
    }
}
