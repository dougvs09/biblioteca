package br.com.biblioteca.controller;

import br.com.biblioteca.domain.Book;
import br.com.biblioteca.domain.PaginationWrapper;
import br.com.biblioteca.mapper.BookResponseMapper;
import br.com.biblioteca.mapper.ListBooksResponseMapper;
import br.com.biblioteca.service.BookService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.BookApi;
import org.openapitools.model.BookResponse;
import org.openapitools.model.CreateBookRequest;
import org.openapitools.model.ListBooksFiltersRequest;
import org.openapitools.model.ListBooksResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class BookController implements BookApi {

    private final BookService bookService;
    private final BookResponseMapper bookResponseMapper;
    private final ListBooksResponseMapper listBooksResponseMapper;

    @Override
    public ResponseEntity<BookResponse> createBook(CreateBookRequest createBookRequest) {
        Book book = bookService.createBook(createBookRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseMapper.toResponse(book));
    }

    @Override
    public ResponseEntity<Void> deleteBook(Integer id) {
        bookService.deleteBook(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<BookResponse> getBook(Integer id) {
        Book book = bookService.getBook(id);

        return ResponseEntity.status(HttpStatus.OK).body(bookResponseMapper.toResponse(book));
    }

    @Override
    public ResponseEntity<ListBooksResponse> listBooks(Integer page, Integer limit, String order, ListBooksFiltersRequest listBooksFiltersRequest) {
        PaginationWrapper<Book> paginationWrapper = bookService.listBooks(limit, page, order, listBooksFiltersRequest);

        return ResponseEntity.status(HttpStatus.OK).body(listBooksResponseMapper.toResponse(paginationWrapper));
    }

    @Override
    public ResponseEntity<BookResponse> rentBook(Integer id) {
        Book book = bookService.rentBook(id);

        return ResponseEntity.status(HttpStatus.OK).body(bookResponseMapper.toResponse(book));
    }

    @Override
    public ResponseEntity<BookResponse> returnBook(Integer id) {
        Book book = bookService.returnBook(id);

        return ResponseEntity.status(HttpStatus.OK).body(bookResponseMapper.toResponse(book));
    }
}
