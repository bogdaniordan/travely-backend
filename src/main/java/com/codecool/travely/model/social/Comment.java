package com.codecool.travely.model.social;

import com.codecool.travely.model.user.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 1)
    private String content;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    private LocalDateTime time;

    @ManyToOne
    private Customer author;

    public Comment(String content, Post post, Customer author, LocalDateTime time) {
        this.content = content;
        this.post = post;
        this.author = author;
        this.time = time;
    }
}
