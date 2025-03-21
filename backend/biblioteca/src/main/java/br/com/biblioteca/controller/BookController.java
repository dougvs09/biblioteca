package br.com.biblioteca.controller;

import br.com.biblioteca.domain.Book;
import br.com.biblioteca.mapper.BookResponseMapper;
import br.com.biblioteca.service.BookService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.BookApi;
import org.openapitools.model.BookResponse;
import org.openapitools.model.CreateBookRequest;
import org.openapitools.model.ListBooksFiltersRequest;
import org.openapitools.model.ListBooksResponseInner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class BookController implements BookApi {

    private final BookService bookService;
    private final BookResponseMapper bookResponseMapper;

    @Override
    public ResponseEntity<BookResponse> createBook(CreateBookRequest createBookRequest) {
        Book book = bookService.create(createBookRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseMapper.toResponse(book));
    }

    @Override
    public ResponseEntity<Void> deleteBook(Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<BookResponse> getBook(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(new BookResponse());
    }

    @Override
    public ResponseEntity<List<ListBooksResponseInner>> listBooks(Integer page, Integer limit, ListBooksFiltersRequest listBooksFiltersRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(List.of(new ListBooksResponseInner()));
    }

    @Override
    public ResponseEntity<BookResponse> rentBook(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(new BookResponse());
    }
}
