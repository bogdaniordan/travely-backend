package com.codecool.travely.controller;

import com.amazonaws.Response;
import com.codecool.travely.model.Comment;
import com.codecool.travely.service.CommentService;
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
@RequestMapping("/comments")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('CUSTOMER')")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/all-for-post/{id}")
    public ResponseEntity<List<Comment>> getAllForPost(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findAllByPostId(id));
    }
}
