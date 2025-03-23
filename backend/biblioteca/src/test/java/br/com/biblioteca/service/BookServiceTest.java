package br.com.biblioteca.service;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.domain.Book;
import br.com.biblioteca.domain.PaginationWrapper;
import br.com.biblioteca.domain.StatusEnum;
import br.com.biblioteca.exception.BookCannotBeDeletedException;
import br.com.biblioteca.exception.BookCannotBeRentedException;
import br.com.biblioteca.exception.BookCannotBeReturnedException;
import br.com.biblioteca.exception.BookNotFoundException;
import br.com.biblioteca.repository.BookAuthorRepository;
import br.com.biblioteca.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.CreateBookRequest;
import org.openapitools.model.ListBooksFiltersRequest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService tested;
    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorService authorService;
    @Mock
    BookAuthorRepository bookAuthorRepository;

    @Test
    @DisplayName("Should create a book")
    void shouldCreateABook() {
        CreateBookRequest createBookRequest = getCreateBookRequest();
        Book book = getBook();
        Integer id = 1;
        when(authorService.create(any())).thenReturn(book.getAuthors().get(0));
        when(authorService.create(any())).thenReturn(book.getAuthors().get(1));
        when(bookRepository.create(any())).thenReturn(id);
        when(bookAuthorRepository.create(any(), any())).thenReturn(book.getAuthors().get(0).getId());
        when(bookAuthorRepository.create(any(), any())).thenReturn(book.getAuthors().get(1).getId());

        Book result = tested.createBook(createBookRequest);

        verify(authorService, times(2)).create(any());
        verify(bookRepository, times(1)).create(any());
        verify(bookAuthorRepository, times(2)).create(any(), any());
        assertAll("Assert result and book", () -> {
           assertEquals(id, result.getId());
           assertEquals(result.getName(), book.getName());
           assertEquals(result.getResume(), book.getResume());
           assertEquals(result.getGenre(), book.getGenre());
           assertEquals(result.getReleaseYear(), book.getReleaseYear());
           assertEquals(result.getAuthors().size(), book.getAuthors().size());
           assertEquals(StatusEnum.AVAILABLE, result.getStatus());
           assertTrue(result.isActive());
        });
    }

    @Test
    @DisplayName("Should list books with filters")
    void shouldListBooksWithFilters() {
        Integer limit = 4;
        Integer page = 1;
        String order = "ASC";
        ListBooksFiltersRequest listBooksFiltersRequest = getFilterRequest();
        Book book = getBook();
        when(bookRepository.countTotal(any(), any(), anyList(), anyList())).thenReturn(1);
        when(bookRepository.list(any(), any(), any(), any(), any(), anyList(), anyList())).thenReturn(List.of(book));

        PaginationWrapper<Book> result = tested.listBooks(limit, page, order, listBooksFiltersRequest);

        verify(bookRepository, times(1)).countTotal(any(), any(), anyList(), anyList());
        verify(bookRepository, times(1)).list(any(), any(), any(), any(), any(), anyList(), anyList());
        assertAll("Assert result", () -> {
            assertEquals(limit, result.getLimit());
            assertEquals(page, result.getPage());
            assertEquals(book, result.getContent().get(0));
            assertEquals(1, result.getTotalPages());
            assertEquals(1, result.getTotal());
            assertFalse(result.getHasPrevious());
            assertFalse(result.getHasNext());
            assertFalse(result.getIsEmpty());
        });
    }

    @Test
    @DisplayName("Should list books with no filters")
    void shouldListBooksWithNoFilters() {
        Integer limit = 2;
        Integer page = 1;
        String order = "ASC";
        ListBooksFiltersRequest listBooksFiltersRequest = new ListBooksFiltersRequest();
        Book book = getBook();
        when(bookRepository.countTotal(any(), any(), anyList(), anyList())).thenReturn(4);
        when(bookRepository.list(any(), any(), any(), any(), any(), anyList(), anyList())).thenReturn(List.of(book, book, book, book));

        PaginationWrapper<Book> result = tested.listBooks(limit, page, order, listBooksFiltersRequest);

        verify(bookRepository, times(1)).countTotal(any(), any(), anyList(), anyList());
        verify(bookRepository, times(1)).list(any(), any(), any(), any(), any(), anyList(), anyList());
        assertAll("Assert result", () -> {
            assertEquals(limit, result.getLimit());
            assertEquals(page, result.getPage());
            assertEquals(book, result.getContent().get(0));
            assertEquals(book, result.getContent().get(1));
            assertEquals(book, result.getContent().get(2));
            assertEquals(book, result.getContent().get(3));
            assertEquals(2, result.getTotalPages());
            assertEquals(4, result.getTotal());
            assertFalse(result.getHasPrevious());
            assertTrue(result.getHasNext());
            assertFalse(result.getIsEmpty());
        });
    }

    @Test
    @DisplayName("Should return empty list of books when no content")
    void shouldReturnEmptyListOfBooksWhenNoContent() {
        Integer limit = 2;
        Integer page = 1;
        String order = "ASC";
        ListBooksFiltersRequest listBooksFiltersRequest = new ListBooksFiltersRequest();
        when(bookRepository.countTotal(any(), any(), anyList(), anyList())).thenReturn(0);
        when(bookRepository.list(any(), any(), any(), any(), any(), anyList(), anyList())).thenReturn(null);

        PaginationWrapper<Book> result = tested.listBooks(limit, page, order, listBooksFiltersRequest);

        verify(bookRepository, times(1)).countTotal(any(), any(), anyList(), anyList());
        verify(bookRepository, times(1)).list(any(), any(), any(), any(), any(), anyList(), anyList());
        assertAll("Assert result", () -> {
            assertEquals(limit, result.getLimit());
            assertEquals(page, result.getPage());
            assertEquals(0, result.getTotalPages());
            assertEquals(0, result.getTotal());
            assertFalse(result.getHasPrevious());
            assertFalse(result.getHasNext());
            assertTrue(result.getIsEmpty());
        });
    }

    @Test
    @DisplayName("Should get a book")
    void shouldGetABook() {
        Integer id = 1;
        Book book = getBookWithoutAuthors();
        when(bookRepository.getBookByBookId(any())).thenReturn(Optional.of(book));
        when(authorService.getAuthorsByBookId(any())).thenReturn(List.of(Author.of(1, "Author 1 test"), Author.of(2, "Author 2 test")));

        Book result = tested.getBook(id);

        verify(bookRepository, times(1)).getBookByBookId(any());
        verify(authorService, times(1)).getAuthorsByBookId(any());
        assertAll("Assert result", () -> {
           assertEquals(result, book);
           assertEquals(2, result.getAuthors().size());
        });
    }

    @Test
    @DisplayName("Should throw exception when book do not exists")
    void shouldThrowExceptionWhenBookDoNotExists() {
        Integer id = 1;
        when(bookRepository.getBookByBookId(id)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> tested.getBook(id));

        verify(bookRepository, times(1)).getBookByBookId(any());
        verify(authorService, times(0)).getAuthorsByBookId(any());
        assertAll("Assert result", () -> {
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            assertEquals("Book not found! Verify the id", exception.getMessage());
        });
    }

    @Test
    @DisplayName("Should rent a book")
    void shouldRentABook() {
        Integer id = 1;
        Book book = getBookWithoutAuthors();
        when(bookRepository.getBookByBookId(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).updateBookStatus(book);

        Book result = tested.rentBook(id);

        verify(bookRepository, times(1)).updateBookStatus(book);
        assertAll("Assert result", () -> {
            assertEquals(StatusEnum.UNAVAILABLE, result.getStatus());
        });
    }

    @Test
    @DisplayName("Should throw exception when book its unavailable")
    void shouldThorExceptionWhenBookItsUnavailable() {
        Integer id = 1;
        Book book = getBookWithoutAuthors();
        book.rent();
        when(bookRepository.getBookByBookId(id)).thenReturn(Optional.of(book));

        BookCannotBeRentedException exception = assertThrows(BookCannotBeRentedException.class, () -> tested.rentBook(id));

        verify(bookRepository, times(0)).updateBookStatus(book);
        assertAll("Assert result", () -> {
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            assertEquals("This book cannot be rented because it's rented now", exception.getMessage());
            assertEquals(StatusEnum.UNAVAILABLE, book.getStatus());
        });
    }

    @Test
    @DisplayName("Should returning a book")
    void shouldReturningABook() {
        Integer id = 1;
        Book book = getBookWithoutAuthors();
        book.rent();
        when(bookRepository.getBookByBookId(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).updateBookStatus(book);

        Book result = tested.returnBook(id);

        verify(bookRepository, times(1)).updateBookStatus(book);
        assertAll("Assert result", () -> {
            assertEquals(StatusEnum.AVAILABLE, result.getStatus());
        });
    }

    @Test
    @DisplayName("Should throw exception when book its available")
    void shouldThorExceptionWhenBookItsAvailable() {
        Integer id = 1;
        Book book = getBookWithoutAuthors();
        when(bookRepository.getBookByBookId(id)).thenReturn(Optional.of(book));

        BookCannotBeReturnedException exception = assertThrows(BookCannotBeReturnedException.class, () -> tested.returnBook(id));

        verify(bookRepository, times(0)).updateBookStatus(book);
        assertAll("Assert result", () -> {
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            assertEquals("This book cannot be returned because it's available now", exception.getMessage());
            assertEquals(StatusEnum.AVAILABLE, book.getStatus());
        });
    }

    @Test
    @DisplayName("Should delete a book")
    void shouldDeleteABook() {
        Integer id = 1;
        Book book = getBookWithoutAuthors();
        when(bookRepository.getBookByBookId(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteBook(book);

        tested.deleteBook(id);

        verify(bookRepository, times(1)).deleteBook(book);
        assertAll("Assert result", () -> {
            assertFalse(book.isActive());
        });
    }

    @Test
    @DisplayName("Should throw exception when book its rented")
    void shouldThorExceptionWhenBookItsRented() {
        Integer id = 1;
        Book book = getBookWithoutAuthors();
        book.rent();
        when(bookRepository.getBookByBookId(id)).thenReturn(Optional.of(book));

        BookCannotBeDeletedException exception = assertThrows(BookCannotBeDeletedException.class, () -> tested.deleteBook(id));

        verify(bookRepository, times(0)).updateBookStatus(book);
        assertAll("Assert result", () -> {
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            assertEquals("This book cannot be returned because it's rented now", exception.getMessage());
            assertTrue(book.isActive());
        });
    }

    private ListBooksFiltersRequest getFilterRequest() {
        ListBooksFiltersRequest listBooksFiltersRequest = new ListBooksFiltersRequest();
        listBooksFiltersRequest.setStatus(ListBooksFiltersRequest.StatusEnum.AVAILABLE);
        listBooksFiltersRequest.setGenres(List.of("Genre 1 test", "Genre 2 test"));
        listBooksFiltersRequest.setReleaseYears(List.of("2020", "2024"));
        return listBooksFiltersRequest;
    }

    private CreateBookRequest getCreateBookRequest() {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setName("Name test");
        createBookRequest.setGenre("Genre test");
        createBookRequest.setResume("Resume test");
        createBookRequest.setReleaseYear("2020");
        createBookRequest.setAuthors(List.of("Author 1 test", "Author 2 test"));
        return createBookRequest;
    }

    private Book getBook() {
        return Book.of(1, "Name test", "Resume test", "2020", "Genre test", List.of(Author.of(1, "Author 1 test"), Author.of(2, "Author 2 test")));
    }

    private Book getBookWithoutAuthors() {
        return Book.of(1, "Name test", "Resume test", "2020", "Genre test", StatusEnum.AVAILABLE);
    }
}
