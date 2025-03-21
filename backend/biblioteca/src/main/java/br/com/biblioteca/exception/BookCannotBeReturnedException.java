package br.com.biblioteca.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookCannotBeReturnedException extends RuntimeException {

    private final HttpStatus statusCode;

    public BookCannotBeReturnedException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
