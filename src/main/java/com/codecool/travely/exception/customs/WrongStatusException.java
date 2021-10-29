package com.codecool.travely.exception.customs;

public class WrongStatusException extends RuntimeException{
    public WrongStatusException(String message) {
        super(message);
    }
}
