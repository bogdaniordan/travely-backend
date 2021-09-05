package com.codecool.travely.controller;

import com.amazonaws.Response;
import com.codecool.travely.model.Cleaner;
import com.codecool.travely.service.CleanerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
