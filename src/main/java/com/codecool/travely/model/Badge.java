package com.codecool.travely.model;

import com.codecool.travely.dto.response.BadgeDto;
import com.codecool.travely.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;

public enum Badge {
    Junior_host("Junior host", "Junior_host.jpg", "Add 3 accommodations to your hosting list."),
    Booking_guru("Booking guru","Booking_guru.jpg", "Get your first booking.");

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
