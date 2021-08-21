package com.codecool.travely.model;

import com.codecool.travely.security.Role;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "customer")
@Data
@NoArgsConstructor
@Builder
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
    @GeneratedValue
    @NotNull
    @Min(5)
    @Max(25)
    private String password;
    @NotNull
    private String address;
    private String phoneNumber;
    private String gender;
    @Min(18)
    private Integer age;

    @ElementCollection
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public Customer(String firstName, String lastName, String username, String email, String password, String address, String phoneNumber, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}
