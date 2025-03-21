package br.com.biblioteca.configuration;

import br.com.biblioteca.exception.BookCannotBeRentedException;
import br.com.biblioteca.exception.BookCannotBeReturnedException;
import br.com.biblioteca.exception.BookNotFoundException;
import org.openapitools.model.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestControllerAdviceExceptionConfiguration {

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

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.UNPROCESSABLE_ENTITY.toString());
        errorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }
}
