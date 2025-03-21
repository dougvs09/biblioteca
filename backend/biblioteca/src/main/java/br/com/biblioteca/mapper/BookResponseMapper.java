package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.domain.Book;
import org.openapitools.model.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookResponseMapper {

    public BookResponse toResponse(Book book) {
        BookResponse bookResponse = new BookResponse();

        bookResponse.setId(book.getId());
        bookResponse.setName(book.getName());
        bookResponse.setResume(book.getResume());
        bookResponse.setGenre(book.getGenre());
        bookResponse.setReleaseYear(book.getReleaseYear());
        bookResponse.setStatus(BookResponse.StatusEnum.fromValue(book.getStatus().getValue()));
        bookResponse.setAuthors(book.getAuthors().stream().map(Author::getName).toList());

        return bookResponse;
    }
}
