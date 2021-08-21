package com.codecool.travely.controller;

import com.codecool.travely.repository.CustomerRepository;
import com.codecool.travely.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

}