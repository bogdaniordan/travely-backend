package com.codecool.travely.service;

import com.codecool.travely.model.social.PostNotification;
import com.codecool.travely.model.user.Customer;
import com.codecool.travely.model.social.Post;
import com.codecool.travely.repository.PostNotificationRepository;
import com.codecool.travely.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final CustomerService customerService;
    private final PostNotificationRepository postNotificationRepository;

    public void save(Post post) {
        postRepository.save(post);
    }

    public void saveNewPost(Post post, Long userId) {
        log.info("User with id " + userId + " is saving a new post.");
        post.setAuthor(customerService.findById(userId));
        post.setTime(LocalDateTime.now());
        save(post);
        notifySubscribedUsers(userId, post);
    }

    public void notifySubscribedUsers(Long userId, Post post) {
        log.info("Notifying subscribed users when user with id " + userId + " has posted.");
        customerService.findAll().forEach(customer -> {
            customer.getUsersToGetToNotifiedFrom().forEach(id -> {
                PostNotification postNotification = new PostNotification(customer, post);
                postNotificationRepository.save(postNotification);
            });
        });
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

    public List<Post> getPostedPosts(Long userId) {
        log.info("Fetching all posts for user with id " + userId);
        return postRepository.findAll().stream().filter(post -> post.getAuthor().getId() == (long) userId).collect(Collectors.toList());
    }

    public List<Post> getLikedPosts(Long userId) {
        log.info("Fetching all posts liked by user with id " + userId);
        return postRepository.findAll().stream().filter(post -> post.getLikes().contains(customerService.findById(userId))).collect(Collectors.toList());
    }

    public void markPostNotificationAsSeen(Long id) {
        log.info("Marking post notification as seen");
        PostNotification postNotification = postNotificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find notification with id " + id));
        postNotification.setSeen(true);
    }


    public HashMap<Long, List<PostNotification>> fetchNotifications(Long userId) {
        log.info("Fetching notifications for user with id " + userId);
        List<PostNotification> postNotifications = postNotificationRepository.findAll().stream()
                .filter(postNotification -> postNotification.getCustomer().getId() == (long) userId && !postNotification.isSeen())
                .collect(Collectors.toList());
        HashMap<Long, List<PostNotification>> mappedNotifications = new HashMap<>();
        postNotifications.forEach(postNotification -> {
            List<PostNotification> lst;
            if(!mappedNotifications.containsKey(postNotification.getCustomer().getId())) {
                lst = new ArrayList<>();
            } else {
                lst = mappedNotifications.get(postNotification.getCustomer().getId());
            }
            lst.add(postNotification);
            mappedNotifications.put(postNotification.getCustomer().getId(), lst);
        });
        return mappedNotifications;
    }
}
