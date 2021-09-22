package com.codecool.travely.controller;

import com.codecool.travely.model.Comment;
import com.codecool.travely.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save-comment/{postId}/{userId}")
    public ResponseEntity<String> saveNewComment(@RequestBody Comment comment, @PathVariable Long postId, @PathVariable Long userId) {
        commentService.saveNewComment(comment, userId, postId);
        return ResponseEntity.ok("Saving a new comment for post with id " + postId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Deleting comment with id " + id);
    }
}
