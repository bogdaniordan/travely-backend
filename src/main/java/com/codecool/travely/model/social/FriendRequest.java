package com.codecool.travely.model.social;

import com.codecool.travely.model.user.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer sender;

    @ManyToOne Customer receiver;

    public FriendRequest(Customer sender, Customer receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}
