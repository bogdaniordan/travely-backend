package com.codecool.travely.service;

import com.codecool.travely.repository.CommentRepository;
import com.codecool.travely.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CommunityService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
}
