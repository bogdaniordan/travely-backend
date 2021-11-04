package com.codecool.travely.model.user;

import com.codecool.travely.model.Accommodation;
import com.codecool.travely.model.CardDetails;
import com.codecool.travely.security.Role;
import com.codecool.travely.security.oauth.AuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "customer")
@Data
@NoArgsConstructor
public class Customer {
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

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;


    @Size(min = 5, max = 50)
    private String address;

    @Size(min = 5, max = 50)
    private String phoneNumber;

    private String gender;

    @Min(value = 18, message = "Age should not be less than 18.")
    private Integer age;

    private String picture;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Role> roles = Set.of(Role.ROLE_CUSTOMER);

    @OneToOne(cascade = CascadeType.ALL)
    private CardDetails cardDetails;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Accommodation> savedAccommodations;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> friends;

    public Customer(String firstName, String lastName, String username, String email, String password, String address, String phoneNumber, String gender, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = age;
    }

    public void addToFavorites(Accommodation accommodation) {
        savedAccommodations.add(accommodation);
    }

    public void removeFromFavorites(Accommodation accommodation) {
        savedAccommodations.remove(accommodation);
    }

    public void addFriend(Long friend) {
        friends.add(friend);
    }

    public void removeFriend(Long friend) {
        friends.remove(friend);
    }
}
