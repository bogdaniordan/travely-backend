package com.codecool.travely.controller;

import com.codecool.travely.model.Question;
import com.codecool.travely.repository.QuestionRepository;
import com.codecool.travely.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('HOST')")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add-question/{customerId}/{hostId}")
    public ResponseEntity<String> addQuestion(@RequestBody Question question, @PathVariable Long customerId, @PathVariable Long hostId) {
        questionService.addQuestion(question, customerId, hostId);
        return ResponseEntity.ok("Question has been added.");
    }

}
