package com.codecool.travely.repository;

import com.codecool.travely.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingsByCustomerId(Long customerId);
    Booking findByAccommodationId(Long id);
}
