package com.codecool.travely.service;

import com.codecool.travely.model.Customer;
import com.codecool.travely.model.Post;
import com.codecool.travely.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final CustomerService customerService;

    public void save(Post post) {
        postRepository.save(post);
    }

    public void saveNewPost(Post post, Long userId) {
        log.info("User with id " + userId + " is saving a new post.");
        post.setAuthor(customerService.findById(userId));
        post.setTime(LocalDateTime.now());
        save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find post with id " + id));
    }

    public List<Post> findAll() {
        log.info("Fetching all posts in chronological order.");
        List<Post> posts = postRepository.findAll();
        posts.sort(Comparator.comparing(Post::getTime).reversed());
        return posts;
    }

    public Boolean postIsLiked(Long postId, Long userId) {
        log.info("Checking if user with id " + userId + " has liked post with id " + postId);
        return findById(postId).getLikes().contains(customerService.findById(userId));
    }

    public void likePost(Long userId, Long postId) {
        log.info("Liking post with id " + postId);
        Post post = findById(postId);
        Customer customer = customerService.findById(userId);
        post.likePost(customer);
        save(post);
    }

    public void unLikePost(Long userId, Long postId) {
        log.info("Unliking post with id " + postId);
        Post post = findById(postId);
        Customer customer = customerService.findById(userId);
        post.unlike(customer);
        save(post);
    }

    public Integer getLikesNumber(Long postId) {
        log.info("Getting likes for post " + postId);
        return findById(postId).getLikes().size();
    }

    public void delete(Long postId) {
        log.info("Deleting post with id " + postId);
        postRepository.delete(findById(postId));
    }

    public List<Post> searchPosts(String input) {
        log.info("Searching posts by content input.");
        return postRepository.findAll().stream().filter(post -> post.getContent().toLowerCase().contains(input.toLowerCase()) || post.getTitle().toLowerCase().contains(input.toLowerCase())).collect(Collectors.toList());
    }
}
