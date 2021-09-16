package com.codecool.travely.controller;

import com.codecool.travely.model.Question;
import com.codecool.travely.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "*")
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

    @GetMapping("/get-all-for-host/{customerId}/{hostId}")
    public ResponseEntity<List<Question>> getAllForEachBooking(@PathVariable Long customerId, @PathVariable Long hostId) {
        return new ResponseEntity<>(questionService.getAllForHostAndCustomer(customerId, hostId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping("/mark-as-solved/{id}")
    public ResponseEntity<String> markQuestionAsSolved(@PathVariable Long id) {
        questionService.markAsSolved(id);
        return ResponseEntity.ok("Question solved state has been changed.");
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping("/mark-as-seen/{id}")
    public ResponseEntity<String> markQuestionAsSeen(@PathVariable Long id) {
        questionService.markAsSeen(id);
        return ResponseEntity.ok("Question has been marked as seen.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.ok("Question has been deleted.");
    }

    @PutMapping("/respond-question/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<String> setResponse(@PathVariable Long id, @RequestBody Question question){
        questionService.updateQuestion(id, question);
        return ResponseEntity.ok("Question has been updated.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestion() {
        return ResponseEntity.ok(questionService.findAll());
    }
}
