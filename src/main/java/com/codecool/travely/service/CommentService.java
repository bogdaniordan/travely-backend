package com.codecool.travely.service;

import com.codecool.travely.model.Comment;
import com.codecool.travely.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final CustomerService customerService;
    private final PostService postService;

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public void saveNewComment(Comment comment, Long userId, Long postId) {
        log.info("User with id " + userId + " is saving a new comment.");
        comment.setPost(postService.findById(postId));
        comment.setAuthor(customerService.findById(userId));
        comment.setTime(LocalDateTime.now());
        save(comment);
    }

    public List<Comment> findAllByPostId(Long id) {
        log.info("Fetching all comments for post with id " + id);
        List<Comment> comments = commentRepository.findAllByPostId(id);
        comments.sort(Comparator.comparing(Comment::getTime).reversed());
        return comments;
    }

}
