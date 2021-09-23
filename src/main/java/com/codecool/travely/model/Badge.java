package com.codecool.travely.model;

import com.codecool.travely.dto.response.BadgeDto;
import com.codecool.travely.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;

public enum Badge {
    JUNIOR_HOST("Junior host", "Junior_host.jpg", "Add 3 accommodations to your hosting list."),
    BOOKING_GURU("Booking guru","Booking_guru.jpg", "Get your first booking."),
    CLEANERS_KING("Cleaners king", "cleaners_king.jpg", "Hire 3 cleaners."),
    SPOTLESS("Spotless", "spotless.jpg", "Clean your first accommodation.");

    public final String name;

    public final String picture;

    public final String description;

    Badge(String name, String picture, String description) {
        this.name = name;
        this.picture = picture;
        this.description = description;
    }

    public BadgeDto badgeToDto(String name, String picture, String description) {
        return new BadgeDto(name, picture, description);
    }
}
