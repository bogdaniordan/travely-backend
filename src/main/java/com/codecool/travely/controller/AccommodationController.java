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
@PreAuthorize("hasRole('CUSTOMER')")
@AllArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/{id}")
    public ResponseEntity<Accommodation> getById(@PathVariable Long id) {
        return new ResponseEntity<>(accommodationService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Accommodation>> filterByLocation(@PathVariable String location) {
        return new ResponseEntity<>(accommodationService.filterByLocation(location), HttpStatus.OK);
    }

    @GetMapping("/place-type/{type}")
    public ResponseEntity<List<Accommodation>> filterByPlaceType(@PathVariable String type) {
        return new ResponseEntity<>(accommodationService.filterByPlaceType(type), HttpStatus.OK);
    }

    @GetMapping("/place-type/{type}/location/{location}")
    public ResponseEntity<List<Accommodation>> filterByLocationAndPlaceType(@PathVariable String type, @PathVariable String location) {
        return new ResponseEntity<>(accommodationService.filterByLocationAndType(location, type), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Accommodation>> getAll() {
        return new ResponseEntity<>(accommodationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get-by-title/{titleInput}")
    public ResponseEntity<List<Accommodation>> getByTitleInput(@PathVariable String titleInput) {
        return new ResponseEntity<>(accommodationService.filterByAccommodationTitle(titleInput), HttpStatus.OK);
    }

    @GetMapping("/save/accommodation/{accommodationId}/customer/{customerId}")
    public ResponseEntity<String> saveAccommodation(@PathVariable Long accommodationId, @PathVariable Long customerId) {
        accommodationService.saveAccommodationToCustomerList(accommodationId, customerId);
        return ResponseEntity.accepted().body("Accommodation added to customer list.");
    }
}
