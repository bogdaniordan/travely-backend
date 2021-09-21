package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany
    private List<Customer> likes = new ArrayList<>();

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
