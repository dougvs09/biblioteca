package br.com.biblioteca.service;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.domain.Book;
import br.com.biblioteca.repository.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @InjectMocks
    AuthorService tested;
    @Mock
    AuthorRepository authorRepository;

    @Test
    @DisplayName("Should create an author when not exists")
    void shouldCreateAnAuthorWhenNotExists() {
        Integer id = 1;
        Author author = getAuthor();
        when(authorRepository.getAuthorByName(author.getName())).thenReturn(Optional.empty());
        when(authorRepository.create(author)).thenReturn(id);

        Author result = tested.create(author);

        verify(authorRepository, times(1)).getAuthorByName(author.getName());
        verify(authorRepository, times(1)).create(author);
        assertAll("Assert result and user", () -> {
            assertEquals(result.getName(), author.getName());
            assertNotNull(result.getId());
        });
    }

    @Test
    @DisplayName("Should not create an author when exists")
    void shouldNotCreateAnAuthorWhenExists() {
        Integer id = 1;
        Author author = getAuthor();
        author.setId(id);
        when(authorRepository.getAuthorByName(author.getName())).thenReturn(Optional.of(author));

        Author result = tested.create(author);

        verify(authorRepository, times(1)).getAuthorByName(author.getName());
        verify(authorRepository, times(0)).create(author);
        assertAll("Assert result and user", () -> {
            assertEquals(result.getName(), author.getName());
            assertEquals(id, result.getId());
        });
    }

    @Test
    @DisplayName("Should get authors with book id")
    void shouldGetAnAuthorsWithBookId() {
        Book book = getBook();
        when(authorRepository.getAuthorsByBookId(book.getId())).thenReturn(book.getAuthors());

        List<Author> result = tested.getAuthorsByBookId(book.getId());

        verify(authorRepository, times(1)).getAuthorsByBookId(book.getId());
        assertAll("Assert result and authors", () -> {
            assertEquals(result, book.getAuthors());
        });
    }

    private Author getAuthor() {
        return Author.of("Test author");
    }

    private Book getBook() {
        return Book.of(1, "Name test", "Resume test", "2020", "Genre test", List.of(Author.of(1, "Author 1 test"), Author.of(2, "Author 2 test")));
    }
}
