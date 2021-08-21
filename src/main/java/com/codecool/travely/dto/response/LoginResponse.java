package com.codecool.travely.dto.response;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String token;
    private String username;
    private List<String> roles;
}
