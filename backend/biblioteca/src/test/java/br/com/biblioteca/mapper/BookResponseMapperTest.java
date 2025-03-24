package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.BookResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BookResponseMapperTest {

    BookResponseMapper tested = new BookResponseMapper();

    @Test
    @DisplayName("Should map to response")
    void shouldMapToResponse() {
        Book book = getBook();

        BookResponse result = tested.toResponse(book);

        assertAll("Assert result map", () -> {
            assertEquals(result.getName(), book.getName());
            assertEquals(result.getId(), book.getId());
            assertEquals(result.getResume(), book.getResume());
            assertEquals(result.getGenre(), book.getGenre());
            assertEquals(result.getReleaseYear(), book.getReleaseYear());
            assertEquals(result.getStatus().getValue(), book.getStatus().getValue());
            assertEquals(result.getAuthors().size(), book.getAuthors().size());
        });
    }

    private Book getBook() {
        return Book.of(1, "Name test", "Resume test", "2020", "Genre test", List.of(Author.of(1, "Author 1 test"), Author.of(2, "Author 2 test")));
    }
}
