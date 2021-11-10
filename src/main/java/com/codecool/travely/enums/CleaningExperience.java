package com.codecool.travely.enums;

import lombok.Getter;

@Getter
public enum CleaningExperience {
    BEGINNER(3),
    INTERMEDIATE(2),
    SENIOR(1);

    private final int cleaningDurationInDays;

    CleaningExperience(int cleaningDurationInDays) {
        this.cleaningDurationInDays = cleaningDurationInDays;
    }
}
