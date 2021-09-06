package com.codecool.travely.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse {
    private String message;
    private String error;

    public GenericResponse(String message) {
        this.message = message;
    }
}
