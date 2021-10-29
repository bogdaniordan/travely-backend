package com.codecool.travely.exception;

import com.codecool.travely.dto.response.HttpResponse;
import com.codecool.travely.exception.customs.AccommodationIdNotFound;
import com.codecool.travely.exception.customs.TitleNotFoundException;
import com.codecool.travely.exception.customs.UsernameNotFoundException;
import com.codecool.travely.exception.customs.WrongStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class APIExceptionHandler {

    private ResponseEntity<HttpResponse> getHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(message, httpStatus, ZonedDateTime.now()), httpStatus);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<HttpResponse> usernameNotFoundException(UsernameNotFoundException exception) {
        return getHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(value = TitleNotFoundException.class)
    public ResponseEntity<HttpResponse> titleNotFoundException(TitleNotFoundException exception) {
        return getHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(value = AccommodationIdNotFound.class)
    public ResponseEntity<HttpResponse> accommodationIdNotFound(AccommodationIdNotFound exception) {
        return getHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(value = WrongStatusException.class)
    public ResponseEntity<HttpResponse> wrongStatusException(WrongStatusException exception) {
        return getHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
