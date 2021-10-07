package com.codecool.travely.repository;

import com.codecool.travely.model.booking.CarBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBookingRepository extends JpaRepository<CarBooking, Long> {
}
