package com.codecool.travely.model.social;

import com.codecool.travely.model.user.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PostNotification {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Post post;

    private boolean seen = false;
}
