package com.codecool.travely.dto.request;

import lombok.Getter;

@Getter
public class StripePaymentDto {
    private String token;
    private double amount;
}
