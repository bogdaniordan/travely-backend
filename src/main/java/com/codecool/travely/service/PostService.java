package com.codecool.travely.service;

import com.codecool.travely.model.Customer;
import com.codecool.travely.model.Post;
import com.codecool.travely.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final CustomerService customerService;

    public void save(Post post) {
        postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find post with id " + id));
    }

    public List<Post> findAll() {
        log.info("Fetching all posts in chronological order.");
        List<Post> posts = postRepository.findAll();
        posts.sort(Comparator.comparing(Post::getTime));
        return posts;
    }

    public Boolean postIsLiked(Long postId, Long userId) {
        log.info("Checking if user with id " + userId + " has liked post with id " + postId);
        return findById(postId).getLikes().contains(customerService.findById(userId));
    }

    public void likePost(Long userId, Long postId) {
        Post post = findById(postId);
        Customer customer = customerService.findById(userId);
        if (post.getLikes().contains(customer)) {
            post.likePost(customer);
            save(post);
        }
    }
}
