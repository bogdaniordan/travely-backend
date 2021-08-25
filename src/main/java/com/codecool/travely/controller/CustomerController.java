package com.codecool.travely.controller;

import com.codecool.travely.model.CardDetails;
import com.codecool.travely.model.Customer;
import com.codecool.travely.repository.CustomerRepository;
import com.codecool.travely.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/save-card/{id}")
    public ResponseEntity<String> saveCardDetails(@Valid CardDetails cardDetails, @PathVariable Long id) {
        customerService.saveCardDetails(cardDetails, id);
        return ResponseEntity.ok("Card details have been saved.");
    }



}
