package br.com.biblioteca.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtTokenException extends RuntimeException {

    private final HttpStatus statusCode;

    public JwtTokenException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
