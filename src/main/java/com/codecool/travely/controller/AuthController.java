package com.codecool.travely.controller;

import com.codecool.travely.dto.response.MessageResponse;
import com.codecool.travely.model.Customer;
import com.codecool.travely.security.Role;
import com.codecool.travely.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class AuthController {

    private final CustomerService customerService;


    @PostMapping("/register-customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
        if (customerService.existsByUsername(customer.getUsername()) || customerService.existsByEmail(customer.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username or email already exists in the database!"));
        }
        Customer registeredCustomer = Customer.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .username(customer.getUsername())
                .email(customer.getEmail())
                .password(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt(12)))
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .gender(customer.getGender())
                .age(customer.getAge())
                .roles(List.of(Role.ROLE_CUSTOMER))
                .build();

        System.out.println(customer);
        customerService.saveCustomer(registeredCustomer);
        //send email ?
        return ResponseEntity.ok(new MessageResponse("Customer has been registered successfully!"))
    }
}
