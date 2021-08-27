package com.codecool.travely.controller;

import com.codecool.travely.model.Testimonial;
import com.codecool.travely.service.TestimonialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testimonials")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('HOST')")
@AllArgsConstructor
public class TestimonialController {

    private final TestimonialService testimonialService;

    @GetMapping("/get-all-for-accommodation/{id}")
    public ResponseEntity<List<Testimonial>> getAllByAccommodationId(@PathVariable Long id) {
        return new ResponseEntity<>(testimonialService.getAllByAccommodationId(id), HttpStatus.OK);
    }
}
