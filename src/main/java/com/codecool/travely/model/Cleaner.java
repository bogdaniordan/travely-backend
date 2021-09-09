package com.codecool.travely.model;

import com.codecool.travely.enums.CleaningExperience;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "cleaner")
@Data
@NoArgsConstructor
public class Cleaner {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private CleaningExperience experience;
    @ManyToOne
    private Accommodation currentCleaningJob;
    private boolean hired;
    @ManyToOne
    private Host employer;

    public Cleaner(String name, CleaningExperience experience) {
        this.name = name;
        this.experience = experience;
    }
}