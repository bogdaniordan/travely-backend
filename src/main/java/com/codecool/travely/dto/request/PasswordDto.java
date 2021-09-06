package com.codecool.travely.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class PasswordDto {
    @NotBlank
    private String token;
    @NotBlank
    @Size(min = 5, max = 30)
    private String password;
}
