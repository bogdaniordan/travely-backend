package com.codecool.travely.model;

import com.codecool.travely.security.Role;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "host")
@Data
@NoArgsConstructor
public class Host {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Min(3)
    @Max(25)
    private String firstName;

    @NotNull
    @Min(3)
    @Max(25)
    private String lastName;

    @NotNull
    @Min(3)
    @Max(25)
    private String username;

    @NotNull
    @Min(12)
    @Max(50)
    private String email;

    @NotNull
    @Min(5)
    @Max(25)
    private String password;

    private String picture;

    @ElementCollection
    List<Role> roles = List.of(Role.ROLE_HOST);

    @ElementCollection
    List<Badge> earnedBadges = new ArrayList<>();

    public Host(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void earnBadge(Badge badge) {
        earnedBadges.add(badge);
    }
}
