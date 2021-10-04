package com.codecool.travely.controller;

import com.codecool.travely.model.Customer;
import com.codecool.travely.model.Recommendation;
import com.codecool.travely.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('CUSTOMER')")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/save/{receiverId}/{senderId}/{accommodationId}")
    public ResponseEntity<String> saveRecommendation(@RequestBody Recommendation recommendation, @PathVariable Long receiverId, @PathVariable Long senderId, @PathVariable Long accommodationId) {
        recommendationService.save(recommendation, senderId, receiverId, accommodationId);
        return ResponseEntity.ok("Recommendation saved.");
    }

    @GetMapping("/all-for/{customerId}")
    public ResponseEntity<List<Recommendation>> getAllForCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(recommendationService.findAllForReceiver(customerId));
    }

    @GetMapping("/get-all-receivers/{senderId}/{accommodationId}")
    public ResponseEntity<List<Customer>> getAllWhoCanReceiveRecommendation(@PathVariable Long senderId, @PathVariable Long accommodationId){
        return ResponseEntity.ok(recommendationService.findAllAvailableForRecommendation(senderId, accommodationId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        recommendationService.delete(id);
        return ResponseEntity.ok("Recommendation has been deleted.");
    }

    @PreAuthorize("hasRole('HOST') or hasRole('CUSTOMER')")
    @GetMapping("/get-all-for-host/{id}")
    public ResponseEntity<List<Recommendation>> getAllForHost(@PathVariable Long id) {
        return ResponseEntity.ok(recommendationService.getAllForHost(id));
    }

}
