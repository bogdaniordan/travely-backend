package com.codecool.travely.controller;

import com.codecool.travely.model.Accommodation;
import com.codecool.travely.model.Testimonial;
import com.codecool.travely.service.TestimonialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add/{accommodationId}/{customerId}")
    public ResponseEntity<String> addAccommodation(@RequestBody @Valid Testimonial testimonial, @PathVariable Long accommodationId, @PathVariable Long customerId) {
        testimonialService.addTestimonial(accommodationId, customerId, testimonial);
        return ResponseEntity.ok("Testimonial added.");
    }

    @GetMapping("/accommodation-is-reviewed/{accommodationId}/{customerId}")
    public ResponseEntity<Boolean> accommodationIsReviewed(@PathVariable Long accommodationId, @PathVariable Long customerId) {
        return ResponseEntity.ok(testimonialService.accommodationIsReviewed(accommodationId, customerId));
    }

    @GetMapping("/average-rating/{accommodationId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long accommodationId) {
        return ResponseEntity.ok(testimonialService.getAverageRating(accommodationId));
    }
}
