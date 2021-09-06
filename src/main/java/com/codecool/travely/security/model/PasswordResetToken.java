package com.codecool.travely.security.model;

import com.codecool.travely.model.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name= "password_reset_token")
@Data
@NoArgsConstructor
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    private Customer user;

    private Date expiryDate;

    public PasswordResetToken(String token, Customer user) {
        this.token = token;
        this.user = user;
    }
}
