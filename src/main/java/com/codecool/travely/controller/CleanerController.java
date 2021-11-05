package com.codecool.travely.controller;

import com.codecool.travely.model.Cleaner;
import com.codecool.travely.service.CleanerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/filter-by-status/{status}")
    public ResponseEntity<List<Cleaner>> filterByStatus(@PathVariable String status) {
        return ResponseEntity.ok(cleanerService.filterByHiringStatus(status));
    }

    @GetMapping("/all-for-host/{id}")
    public ResponseEntity<List<Cleaner>> getAllForHost(@PathVariable Long id) {
        return ResponseEntity.ok(cleanerService.getAllCleanersForHost(id));
    }

    @GetMapping("/can-be-cleaned/{accommodationId}")
    public ResponseEntity<Boolean> accommodationCanBeCleaned(@PathVariable Long accommodationId) {
        return ResponseEntity.ok(cleanerService.accommodationCanBeCleaned(accommodationId));
    }

    @GetMapping("/set-clean/{cleanerId}/{accommodationId}")
    public ResponseEntity<String> setCleanerToCleanAccommodation(@PathVariable Long cleanerId, @PathVariable Long accommodationId) {
        cleanerService.setCleanerToCleanAccommodation(cleanerId, accommodationId);
        return ResponseEntity.ok("Set cleaner with id " + cleanerId + " to clean " + accommodationId);
    }

    @GetMapping("/accommodation-is-cleaned-by/{accommodationId}")
    public ResponseEntity<List<Cleaner>> accommodationIsCleanedBy(@PathVariable Long accommodationId) {
        return ResponseEntity.ok(cleanerService.accommodationIsCleanedBy(accommodationId));
    }

    @GetMapping("/set-to-cleaned/{accommodationId}")
    public ResponseEntity<Boolean> setAccommodationToCleaned(@PathVariable Long accommodationId) {
        return ResponseEntity.ok(cleanerService.setAccommodationToCleaned(accommodationId));
    }
}
