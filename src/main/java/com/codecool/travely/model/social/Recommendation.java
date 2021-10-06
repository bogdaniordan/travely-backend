package com.codecool.travely.model.social;

import com.codecool.travely.model.Accommodation;
import com.codecool.travely.model.user.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accommodation accommodation;

    @ManyToOne
    private Customer sender;

    @ManyToOne
    private Customer receiver;

    public Recommendation(String message, Accommodation accommodation, Customer sender, Customer receiver) {
        this.message = message;
        this.accommodation = accommodation;
        this.sender = sender;
        this.receiver = receiver;
    }
}
