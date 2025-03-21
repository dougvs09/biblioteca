package br.com.biblioteca.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private final HttpStatus statusCode;

    public UserAlreadyExistsException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
