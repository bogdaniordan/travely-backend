package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "card_details")
@Data
@NoArgsConstructor
public class CardDetails {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 5)
    private String cardName;
    @NotNull
    @Size(min = 9)
    private String cardNumber;
    @NotNull
    private String expirationDate;
    @NotNull
    @Size(min = 3)
    private String cvv;
}
