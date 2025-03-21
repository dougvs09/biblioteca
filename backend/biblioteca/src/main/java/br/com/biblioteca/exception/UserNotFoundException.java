package br.com.biblioteca.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final HttpStatus statusCode;

    public UserNotFoundException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
