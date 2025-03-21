package br.com.biblioteca.configuration;

import br.com.biblioteca.exception.*;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.openapitools.model.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
public class RestControllerAdviceExceptionConfiguration extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BookCannotBeRentedException.class})
    public ResponseEntity<ErrorResponse> bookCannotBeRentedException(BookCannotBeRentedException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(e.getStatusCode().toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler({BookCannotBeReturnedException.class})
    public ResponseEntity<ErrorResponse> bookCannotBeReturnedException(BookCannotBeReturnedException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(e.getStatusCode().toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<ErrorResponse> bookNotFoundException(BookNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(e.getStatusCode().toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler({BookCannotBeDeletedException.class})
    public ResponseEntity<ErrorResponse> bookCannotBeDeletedException(BookCannotBeDeletedException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(e.getStatusCode().toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler({JwtTokenException.class})
    public ResponseEntity<ErrorResponse> jwtTokenException(JwtTokenException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(e.getStatusCode().toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> userAlreadyExistsException(UserAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(e.getStatusCode().toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> userNotFoundException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(e.getStatusCode().toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler({JWTCreationException.class})
    public ResponseEntity<ErrorResponse> jwtCreationException(JWTCreationException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({JWTVerificationException.class})
    public ResponseEntity<ErrorResponse> jwtVerificationException(JWTVerificationException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.UNAUTHORIZED.toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
