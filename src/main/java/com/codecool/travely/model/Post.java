package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private LocalDateTime time;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Customer> likes = new HashSet<>();

    @ManyToOne
    private Customer author;

    public Post(String title, String content, Customer author, LocalDateTime time) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.time = time;
    }

    public void likePost(Customer customer) {
        likes.add(customer);
    }

    public void unlike(Customer customer) {
        likes.remove(customer);
    }
}
