package com.codecool.travely.service;

import com.codecool.travely.model.Comment;
import com.codecool.travely.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findAllByPostId(Long id) {
        log.info("Fetching all comments for post with id " + id);
        return commentRepository.findAllByPostId(id);
    }

}
