package com.codecool.travely.model.social;

import com.codecool.travely.model.user.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 3)
    private String title;

    @NotNull
    @Size(min = 3)
    private String content;

    private LocalDateTime time;

    @NotNull
    private String location;

    @ManyToMany
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
