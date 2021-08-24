package com.codecool.travely.model;

import com.codecool.travely.enums.CardType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "card_details")
@Data
@NoArgsConstructor
public class CardDetails {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private CardType cardType;
    @NotNull
    @Size(min = 5)
    private String cardName;
    @NotNull
    private String cardNumber;
    @NotNull
    private String expirationDate;
    @Size(min = 3)
    @NotNull
    private String CVV;

}
