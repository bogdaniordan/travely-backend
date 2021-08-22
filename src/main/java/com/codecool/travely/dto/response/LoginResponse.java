package com.codecool.travely.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Long id;
    private String token;
    private String username;
    private List<String> roles;
}
