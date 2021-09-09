package com.codecool.travely.controller;

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

    @PostMapping("/save/{receiverId}/{senderId}")
    public ResponseEntity<String> saveRecommendation(@RequestBody Recommendation recommendation, @PathVariable Long receiverId, @PathVariable Long senderId) {
        recommendationService.save(recommendation, senderId, receiverId);
        return ResponseEntity.ok("Recommendation saved.");
    }

    @GetMapping("/all-for/{customerId}")
    public ResponseEntity<List<Recommendation>> getAllForCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(recommendationService.findAllForReceiver(customerId));
    }

}
