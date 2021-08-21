package com.codecool.travely.controller;

import com.codecool.travely.model.Customer;
import com.codecool.travely.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class AuthController {

    private final CustomerService customerService;


    @PostMapping("/register-customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {

    }
}
