package com.codecool.travely.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
    private String gender;
    private String firstName;
    private String lastName;
    private String password;
}
