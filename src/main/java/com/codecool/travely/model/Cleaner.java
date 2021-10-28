package com.codecool.travely.model;

import com.codecool.travely.enums.CleaningExperience;
import com.codecool.travely.model.user.Host;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accommodation currentCleaningJob;

    private boolean hired;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Accommodation> cleaningHistory;

    @ManyToOne
    private Host employer;

    public Cleaner(String name, CleaningExperience experience) {
        this.name = name;
        this.experience = experience;
    }

    public void addToCleaningHistory(Accommodation accommodation) {
        cleaningHistory.add(accommodation);
    }
}
