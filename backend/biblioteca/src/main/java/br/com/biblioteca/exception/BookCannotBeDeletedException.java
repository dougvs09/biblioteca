package br.com.biblioteca.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookCannotBeDeletedException extends RuntimeException {

    private final HttpStatus statusCode;

    public BookCannotBeDeletedException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
