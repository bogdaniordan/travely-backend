package com.codecool.travely.controller;

import com.codecool.travely.model.Accommodation;
import com.codecool.travely.service.AccommodationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accommodations")
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasAnyRole('CUSTOMER')")
@AllArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Accommodation>> filterByLocation(@PathVariable String location) {
        return new ResponseEntity<>(accommodationService.filterByLocation(location), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Accommodation>> getAll() {
        return new ResponseEntity<>(accommodationService.findAll(), HttpStatus.OK);
    }
}
