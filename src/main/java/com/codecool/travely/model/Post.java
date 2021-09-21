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

    private LocalDateTime time = LocalDateTime.now();

    @OneToMany
    private List<Customer> likes = new ArrayList<>();

    @ManyToOne
    private Customer author;

    public Post(String title, String content, Customer author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void likePost(Customer customer) {
        likes.add(customer);
    }
}
