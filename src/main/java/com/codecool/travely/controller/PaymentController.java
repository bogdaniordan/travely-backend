package com.codecool.travely.controller;

import com.codecool.travely.dto.request.StripePaymentDto;
import com.codecool.travely.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('CUSTOMER')")
public class PaymentController {

    private final StripeService stripeService;

    @Autowired
    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/charge-card")
    public ResponseEntity<String> chargeCard(@RequestBody StripePaymentDto stripePaymentDto) throws Exception {
        stripeService.chargeNewCard(stripePaymentDto.getToken(), stripePaymentDto.getAmount());
        return ResponseEntity.ok("Payment has been charged.");
    }
}
