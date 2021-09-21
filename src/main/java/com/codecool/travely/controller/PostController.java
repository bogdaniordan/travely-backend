package com.codecool.travely.controller;

import com.codecool.travely.model.Post;
import com.codecool.travely.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('CUSTOMER')")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPostsSorted() {
        return ResponseEntity.ok(postService.findAll());
    }
}
