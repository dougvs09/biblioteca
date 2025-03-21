package br.com.biblioteca.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookNotFoundException extends RuntimeException {

    private final HttpStatus statusCode;

    public BookNotFoundException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
