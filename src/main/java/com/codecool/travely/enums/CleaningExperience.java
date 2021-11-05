package com.codecool.travely.enums;

import lombok.Getter;

@Getter
public enum CleaningExperience {
    BEGINNER(1),
    INTERMEDIATE(2),
    SENIOR(3);

    private final int cleaningDurationInDays;

    CleaningExperience(int cleaningDurationInDays) {
        this.cleaningDurationInDays = cleaningDurationInDays;
    }
}
