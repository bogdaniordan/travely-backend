package com.codecool.travely.service;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StripeService {

    @Autowired
    public StripeService() {
        Stripe.apiKey = "sk_test_51JediMF8Clxej3cv3ic64iEpced1LHXQ4rKYZAJPzBVMGPEntq6c86GEz7xi1FiUqxAZgAITdgfLPlHQIK1fUpqH00ydKeBLjv";
    }

    public void chargeNewCard(String token, double amount) throws Exception {
        log.info("Charging a new stripe payment.");
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge.create(chargeParams);
    }
}
