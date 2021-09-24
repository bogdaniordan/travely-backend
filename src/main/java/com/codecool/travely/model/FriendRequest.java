package com.codecool.travely.model;

import com.codecool.travely.enums.FriendRequestStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;

    public FriendRequest(Customer sender, Customer receiver, FriendRequestStatus status) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }
}
