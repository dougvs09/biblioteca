package br.com.biblioteca.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookCannotBeRentedException extends RuntimeException {

    private final HttpStatus statusCode;

    public BookCannotBeRentedException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
