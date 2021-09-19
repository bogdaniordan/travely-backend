package com.codecool.travely.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookingDatesDto {
    private LocalDate checkIn;
    private LocalDate checkOut;
}
