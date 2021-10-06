package com.codecool.travely.security;

import com.codecool.travely.model.user.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity(name= "password_reset_token")
@Data
@NoArgsConstructor
public class PasswordResetToken {
    private static final int EXPIRATION = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    private Customer user;

    private Date expiryDate;

    public PasswordResetToken(String token, Customer user) {
        setTokenExpirationDate();
        this.token = token;
        this.user = user;
    }

    public void setTokenExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, EXPIRATION);
        setExpiryDate(calendar.getTime());
    }
}
