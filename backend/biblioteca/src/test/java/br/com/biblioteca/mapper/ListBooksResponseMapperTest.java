package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.domain.Book;
import br.com.biblioteca.domain.PaginationWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.ListBooksResponse;
import org.openapitools.model.ListBooksResponseContentInner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ListBooksResponseMapperTest {

    ListBooksResponseMapper tested = new ListBooksResponseMapper();

    @Test
    @DisplayName("Should map to response")
    void shouldMapToResponse() {
        PaginationWrapper<Book> paginationWrapper = getPaginationWrapper();

        ListBooksResponse result = tested.toResponse(paginationWrapper);

        assertAll("Assert result map", () -> {
            assertEquals(1, result.getContent().size());
            assertFalse(result.getHasNext());
            assertFalse(result.getHasPrevious());
            assertEquals(1, result.getPage());
            assertEquals(10, result.getLimit());
            assertEquals(1, result.getTotal());
            assertEquals(1, result.getTotalPages());
            assertFalse(result.getIsEmpty());
        });
    }

    private PaginationWrapper<Book> getPaginationWrapper() {
        return new PaginationWrapper<>(1, 10, 1, List.of(getBook()));
    }

    private Book getBook() {
        return Book.of(1, "Name test", "Resume test", "2020", "Genre test", List.of(Author.of(1, "Author 1 test"), Author.of(2, "Author 2 test")));
    }

}
