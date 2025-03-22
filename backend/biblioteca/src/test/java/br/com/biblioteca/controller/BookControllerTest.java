package br.com.biblioteca.controller;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.domain.Book;
import br.com.biblioteca.domain.PaginationWrapper;
import br.com.biblioteca.domain.StatusEnum;
import br.com.biblioteca.mapper.BookResponseMapper;
import br.com.biblioteca.mapper.ListBooksResponseMapper;
import br.com.biblioteca.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    BookController tested;
    @Mock
    BookService bookService;
    @Mock
    BookResponseMapper bookResponseMapper;
    @Mock
    ListBooksResponseMapper listBooksResponseMapper;

    @Test
    @DisplayName("Should create a book")
    void shouldCreateABook() {
        CreateBookRequest createBookRequest = this.getBookRequest();
        Book createdBook = getBook();
        BookResponse bookResponse = getBookResponse();
        when(bookService.createBook(createBookRequest)).thenReturn(createdBook);
        when(bookResponseMapper.toResponse(createdBook)).thenReturn(bookResponse);

        ResponseEntity<BookResponse> result = tested.createBook(createBookRequest);

        verify(bookService, times(1)).createBook(createBookRequest);
        verify(bookResponseMapper, times(1)).toResponse(createdBook);
        assertAll("Assert result and bookResponse", () -> {
            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertEquals(result.getBody().getId(),  bookResponse.getId());
            assertEquals(result.getBody().getAuthors(),  bookResponse.getAuthors());
            assertEquals(result.getBody().getGenre(),  bookResponse.getGenre());
            assertEquals(result.getBody().getResume(),  bookResponse.getResume());
            assertEquals(result.getBody().getReleaseYear(),  bookResponse.getReleaseYear());
            assertEquals(result.getBody().getName(),  bookResponse.getName());
        });
        assertAll("Assert createdBook", () -> {
            assertEquals(StatusEnum.AVAILABLE, createdBook.getStatus());
            assertTrue(createdBook.isActive());
        });
    }

    @Test
    @DisplayName("Should delete a book")
    void shouldDeleteABook() {
        Integer id = 1;
        doNothing().when(bookService).deleteBook(id);

        ResponseEntity<Void> result = tested.deleteBook(id);

        verify(bookService, times(1)).deleteBook(id);
        assertAll("Assert result", () -> {
           assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
           assertNull(result.getBody());
        });
    }

    @Test
    @DisplayName("Should get a book")
    void shouldGetABook() {
        Integer id = 1;
        Book book = getBook();
        BookResponse bookResponse = getBookResponse();
        when(bookService.getBook(id)).thenReturn(book);
        when(bookResponseMapper.toResponse(book)).thenReturn(bookResponse);

        ResponseEntity<BookResponse> result = tested.getBook(id);

        verify(bookService, times(1)).getBook(id);
        verify(bookResponseMapper, times(1)).toResponse(book);
        assertAll("Assert result and bookResponse", () -> {
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(result.getBody().getId(),  bookResponse.getId());
            assertEquals(result.getBody().getAuthors(),  bookResponse.getAuthors());
            assertEquals(result.getBody().getGenre(),  bookResponse.getGenre());
            assertEquals(result.getBody().getResume(),  bookResponse.getResume());
            assertEquals(result.getBody().getReleaseYear(),  bookResponse.getReleaseYear());
            assertEquals(result.getBody().getName(),  bookResponse.getName());
        });
        assertAll("Assert book", () -> {
            assertTrue(book.isActive());
        });
    }

    @Test
    @DisplayName("Should list a books with filter")
    void shouldListABooksWithFilter() {
        Integer page = 1;
        Integer limit = 10;
        String order = "ASC";
        ListBooksFiltersRequest listBooksFiltersRequest = getFilterRequest();
        PaginationWrapper<Book> paginationWrapper = getPaginationWrapper();
        ListBooksResponse listBooksResponse = getListBooksResponse();
        when(bookService.listBooks(limit, page, order, listBooksFiltersRequest)).thenReturn(paginationWrapper);
        when(listBooksResponseMapper.toResponse(paginationWrapper)).thenReturn(listBooksResponse);

        ResponseEntity<ListBooksResponse> result = tested.listBooks(page, limit, order, listBooksFiltersRequest);

        verify(bookService, times(1)).listBooks(limit, page, order, listBooksFiltersRequest);
        verify(listBooksResponseMapper, times(1)).toResponse(paginationWrapper);
        assertAll("Assert result and listBooksResponse", () -> {
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(result.getBody().getContent(), listBooksResponse.getContent());
            assertEquals(result.getBody().getPage(), listBooksResponse.getPage());
            assertEquals(result.getBody().getTotalPages(), listBooksResponse.getTotalPages());
            assertEquals(result.getBody().getTotal(), listBooksResponse.getTotal());
            assertEquals(result.getBody().getLimit(), listBooksResponse.getLimit());
            assertEquals(result.getBody().getIsEmpty(), listBooksResponse.getIsEmpty());
            assertEquals(result.getBody().getHasNext(), listBooksResponse.getHasNext());
            assertEquals(result.getBody().getHasPrevious(), listBooksResponse.getHasPrevious());
        });
    }

    @Test
    @DisplayName("Should return empty content when not exist list of books with filter")
    void shouldReturnEmptyContent() {
        Integer page = 1;
        Integer limit = 10;
        String order = "ASC";
        ListBooksFiltersRequest listBooksFiltersRequest = getFilterRequest();
        PaginationWrapper<Book> paginationWrapper = getPaginationWrapperEmptyContent();
        ListBooksResponse listBooksResponse = getListBooksResponseEmptyContent();
        when(bookService.listBooks(limit, page, order, listBooksFiltersRequest)).thenReturn(paginationWrapper);
        when(listBooksResponseMapper.toResponse(paginationWrapper)).thenReturn(listBooksResponse);

        ResponseEntity<ListBooksResponse> result = tested.listBooks(page, limit, order, listBooksFiltersRequest);

        verify(bookService, times(1)).listBooks(limit, page, order, listBooksFiltersRequest);
        verify(listBooksResponseMapper, times(1)).toResponse(paginationWrapper);
        assertAll("Assert result and listBooksResponse", () -> {
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertTrue(result.getBody().getContent().isEmpty());
            assertEquals(result.getBody().getPage(), listBooksResponse.getPage());
            assertEquals(result.getBody().getTotalPages(), listBooksResponse.getTotalPages());
            assertEquals(result.getBody().getTotal(), listBooksResponse.getTotal());
            assertEquals(result.getBody().getLimit(), listBooksResponse.getLimit());
            assertEquals(result.getBody().getIsEmpty(), listBooksResponse.getIsEmpty());
            assertEquals(result.getBody().getHasNext(), listBooksResponse.getHasNext());
            assertEquals(result.getBody().getHasPrevious(), listBooksResponse.getHasPrevious());
        });
    }

    @Test
    @DisplayName("Should rent a book")
    void shouldRentABook() {
        Integer id = 1;
        Book book = getBookRented();
        BookResponse bookResponse = getBookResponseRented();
        when(bookService.rentBook(id)).thenReturn(book);
        when(bookResponseMapper.toResponse(book)).thenReturn(bookResponse);

        ResponseEntity<BookResponse> result = tested.rentBook(id);

        verify(bookService, times(1)).rentBook(id);
        verify(bookResponseMapper, times(1)).toResponse(book);
        assertAll("Assert result and bookResponse", () -> {
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(result.getBody().getId(),  bookResponse.getId());
            assertEquals(result.getBody().getAuthors(),  bookResponse.getAuthors());
            assertEquals(result.getBody().getGenre(),  bookResponse.getGenre());
            assertEquals(result.getBody().getResume(),  bookResponse.getResume());
            assertEquals(result.getBody().getReleaseYear(),  bookResponse.getReleaseYear());
            assertEquals(result.getBody().getName(),  bookResponse.getName());
        });
    }

    @Test
    @DisplayName("Should return a book")
    void shouldReturnABook() {
        Integer id = 1;
        Book book = getBook();
        BookResponse bookResponse = getBookResponse();
        when(bookService.returnBook(id)).thenReturn(book);
        when(bookResponseMapper.toResponse(book)).thenReturn(bookResponse);

        ResponseEntity<BookResponse> result = tested.returnBook(id);

        verify(bookService, times(1)).returnBook(id);
        verify(bookResponseMapper, times(1)).toResponse(book);
        assertAll("Assert result and bookResponse", () -> {
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(result.getBody().getId(),  bookResponse.getId());
            assertEquals(result.getBody().getAuthors(),  bookResponse.getAuthors());
            assertEquals(result.getBody().getGenre(),  bookResponse.getGenre());
            assertEquals(result.getBody().getResume(),  bookResponse.getResume());
            assertEquals(result.getBody().getReleaseYear(),  bookResponse.getReleaseYear());
            assertEquals(result.getBody().getName(),  bookResponse.getName());
        });
    }

    private ListBooksFiltersRequest getFilterRequest() {
        ListBooksFiltersRequest listBooksFiltersRequest = new ListBooksFiltersRequest();
        listBooksFiltersRequest.setStatus(ListBooksFiltersRequest.StatusEnum.AVAILABLE);
        listBooksFiltersRequest.setGenres(List.of("Genre 1 test", "Genre 2 test"));
        listBooksFiltersRequest.setReleaseYears(List.of("2020", "2024"));
        return listBooksFiltersRequest;
    }

    private PaginationWrapper<Book> getPaginationWrapper() {
        return new PaginationWrapper<>(1, 10, 1, List.of(getBook()));
    }

    private PaginationWrapper<Book> getPaginationWrapperEmptyContent() {
        return new PaginationWrapper<>(1, 10, 0, null);
    }

    private ListBooksResponse getListBooksResponseEmptyContent() {
        ListBooksResponse listBooksResponse = new ListBooksResponse();
        listBooksResponse.setContent(Collections.emptyList());
        listBooksResponse.setTotal(getPaginationWrapper().getTotal());
        listBooksResponse.setTotalPages(getPaginationWrapper().getTotalPages());
        listBooksResponse.setPage(getPaginationWrapper().getPage());
        listBooksResponse.setLimit(getPaginationWrapper().getLimit());
        listBooksResponse.setIsEmpty(getPaginationWrapper().getIsEmpty());
        listBooksResponse.setHasPrevious(getPaginationWrapper().getHasPrevious());
        listBooksResponse.setHasNext(getPaginationWrapper().getHasNext());
        return listBooksResponse;
    }

    private ListBooksResponse getListBooksResponse() {
        ListBooksResponse listBooksResponse = new ListBooksResponse();
        listBooksResponse.setContent(getListBooksResponseContent());
        listBooksResponse.setTotal(getPaginationWrapper().getTotal());
        listBooksResponse.setTotalPages(getPaginationWrapper().getTotalPages());
        listBooksResponse.setPage(getPaginationWrapper().getPage());
        listBooksResponse.setLimit(getPaginationWrapper().getLimit());
        listBooksResponse.setIsEmpty(getPaginationWrapper().getIsEmpty());
        listBooksResponse.setHasPrevious(getPaginationWrapper().getHasPrevious());
        listBooksResponse.setHasNext(getPaginationWrapper().getHasNext());
        return listBooksResponse;
    }

    private List<ListBooksResponseContentInner> getListBooksResponseContent() {
        ListBooksResponseContentInner listBooksResponseContentInner = new ListBooksResponseContentInner();
        listBooksResponseContentInner.setStatus(ListBooksResponseContentInner.StatusEnum.fromValue(getBook().getStatus().getValue()));
        listBooksResponseContentInner.setName(getBook().getName());
        listBooksResponseContentInner.setId(getBook().getId());
        return List.of(listBooksResponseContentInner);
    }

    private CreateBookRequest getBookRequest() {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setName("Name test");
        createBookRequest.setGenre("Genre test");
        createBookRequest.setResume("Resume test");
        createBookRequest.setReleaseYear("2020");
        createBookRequest.setAuthors(List.of("Author 1 test", "Author 2 test"));
        return createBookRequest;
    }

    private BookResponse getBookResponse() {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setName("Name test");
        bookResponse.setGenre("Genre test");
        bookResponse.setResume("Resume test");
        bookResponse.setReleaseYear("2020");
        bookResponse.setAuthors(List.of("Author 1 test", "Author 2 test"));
        bookResponse.setStatus(BookResponse.StatusEnum.AVAILABLE);
        return bookResponse;
    }

    private BookResponse getBookResponseRented() {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setName("Name test");
        bookResponse.setGenre("Genre test");
        bookResponse.setResume("Resume test");
        bookResponse.setReleaseYear("2020");
        bookResponse.setAuthors(List.of("Author 1 test", "Author 2 test"));
        bookResponse.setStatus(BookResponse.StatusEnum.UNAVAILABLE);
        return bookResponse;
    }

    private Book getBook() {
        return Book.of(1, "Name test", "Resume test", "2020", "Genre test", List.of(Author.of(1, "Author 1 test"), Author.of(2, "Author 2 test")));
    }

    private Book getBookRented() {
        return Book.of(1, "Name test", "Resume test", "2020", "Genre test", StatusEnum.UNAVAILABLE);
    }
}
