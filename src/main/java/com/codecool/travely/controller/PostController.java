package com.codecool.travely.controller;

import com.codecool.travely.model.social.Comment;
import com.codecool.travely.model.social.Post;
import com.codecool.travely.model.social.PostNotification;
import com.codecool.travely.service.CommentService;
import com.codecool.travely.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('CUSTOMER')")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPostsSorted() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/is-liked/{postId}/{userId}")
    public ResponseEntity<Boolean> postIsLikedByUser(@PathVariable Long postId, @PathVariable Long userId) {
        return ResponseEntity.ok(postService.postIsLiked(postId, userId));
    }

    @GetMapping("/like-post/{postId}/{userId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        postService.likePost(userId, postId);
        return ResponseEntity.ok("Post with id " + postId + " was liked by user with id " + userId);
    }

    @GetMapping("/unlike-post/{postId}/{userId}")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId, @PathVariable Long userId) {
        postService.unLikePost(userId, postId);
        return ResponseEntity.ok("Post with id " + postId + " was unliked by user with id " + userId);
    }

    @GetMapping("/get-likes/{id}")
    public ResponseEntity<Integer> getLikesNumber(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getLikesNumber(id));
    }

    @PostMapping("/add-post/{userId}")
    public ResponseEntity<String> addPost(@Valid @RequestBody Post post, @PathVariable Long userId) {
        postService.saveNewPost(post, userId);
        return ResponseEntity.ok("Post has been saved.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok("Post has been deleted.");
    }

    @GetMapping("/search-posts/{searchInput}")
    public ResponseEntity<List<Post>> searchPosts(@PathVariable String searchInput) {
        return ResponseEntity.ok(postService.searchPosts(searchInput));
    }

    @GetMapping("/posted-posts/{userId}")
    public ResponseEntity<List<Post>> getPostedPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostedPosts(userId));
    }

    @GetMapping("/liked-posts/{userId}")
    public ResponseEntity<List<Post>> getLikedPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getLikedPosts(userId));
    }

    @GetMapping("/posted-comments/{userId}")
    public ResponseEntity<List<Comment>> getPostedComments(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.getPostedComments(userId));
    }

    @GetMapping("/posts-notifications/{userId}")
    public ResponseEntity<HashMap<Long, List<PostNotification>>> getPostNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.fetchNotifications(userId));
    }

    @GetMapping("/mark-notifications-as-seen/{notificationId}")
    public ResponseEntity<String> markNotificationsAsSeen(@PathVariable Long notificationId) {
        postService.markPostNotificationAsSeen(notificationId);
        return ResponseEntity.ok("Marking post notification with id " + notificationId + " as seen.");
    }
}
