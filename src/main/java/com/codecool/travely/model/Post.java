package com.codecool.travely.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    private LocalDateTime time = LocalDateTime.now();

    @OneToMany
    private List<Customer> likes;

    @ManyToOne
    private Customer author;
}
