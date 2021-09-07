package com.codecool.travely.controller;

import com.amazonaws.Response;
import com.codecool.travely.model.Cleaner;
import com.codecool.travely.service.CleanerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cleaners")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('HOST')")
@AllArgsConstructor
public class CleanerController {

    private final CleanerService cleanerService;

    @GetMapping("/all")
    public ResponseEntity<List<Cleaner>> getAll() {
        return ResponseEntity.ok(cleanerService.findAll());
    }

    @GetMapping("/hire-cleaner/{cleanerId}/{hostId}")
    public ResponseEntity<String> hireCleaner(@PathVariable Long cleanerId, @PathVariable Long hostId) {
        cleanerService.hireCleaner(cleanerId,hostId);
        return ResponseEntity.ok("Cleaner hired.");
    }

    @GetMapping("/fire-cleaner/{cleanerId}")
    public ResponseEntity<String> fireCleaner(@PathVariable Long cleanerId) {
        cleanerService.fireCleaner(cleanerId);
        return ResponseEntity.ok("Cleaner fired.");
    }

    @GetMapping("/clean-accommodation/{cleanerId}/{accommodationId}")
    public ResponseEntity<String> setCleanerToAccommodation(@PathVariable Long cleanerId, @PathVariable Long accommodationId) {
        cleanerService.cleanAccommodation(cleanerId, accommodationId);
        return ResponseEntity.ok("Cleaner with id + " + cleanerId +  " has been set to clean the accommodation with id " + accommodationId);
    }

    @GetMapping("/filter-by-status/{status}")
    public ResponseEntity<List<Cleaner>> filterByStatus(@PathVariable String status) {
        return ResponseEntity.ok(cleanerService.filterByHiringStatus(status));
    }

    @GetMapping("/all-for-host/{id}")
    public ResponseEntity<List<Cleaner>> getAllForHost(@PathVariable Long id) {
        return ResponseEntity.ok(cleanerService.getAllCleanersForHost(id));
    }
}
