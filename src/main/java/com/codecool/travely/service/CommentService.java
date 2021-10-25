package com.codecool.travely.service;

import com.codecool.travely.model.social.Comment;
import com.codecool.travely.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find comment with id " + id));
    }

    public List<Comment> findAllByPostId(Long id) {
        log.info("Fetching all comments for post with id " + id);
        List<Comment> comments = commentRepository.findAllByPostId(id);
        comments.sort(Comparator.comparing(Comment::getTime));
        return comments;
    }

    public void deleteComment(Long id) {
        log.info("Deleting comment with id " + id);
        commentRepository.delete(findById(id));
    }

    public List<Comment> getPostedComments(Long userId) {
        log.info("Fetching all comments posted by user with id " + userId);
        return commentRepository.findAll().stream().filter(comment -> comment.getAuthor().getId() == (long) userId).collect(Collectors.toList());
    }

}
