package br.com.biblioteca.service;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.domain.Book;
import br.com.biblioteca.domain.PaginationWrapper;
import br.com.biblioteca.domain.StatusEnum;
import br.com.biblioteca.exception.BookNotFoundException;
import br.com.biblioteca.repository.BookAuthorRepository;
import br.com.biblioteca.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.CreateBookRequest;
import org.openapitools.model.ListBooksFiltersRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookAuthorRepository bookAuthorRepository;

    @Transactional
    public Book createBook(CreateBookRequest request) {
        List<Author> authors = request.getAuthors().stream().map(Author::of).toList();
        Book book = Book.of(request.getName(), request.getResume(), request.getReleaseYear(), request.getGenre(), authors);

        List<Integer> authorsId = authors.stream().map(author -> authorService.create(author).getId()).toList();

        Integer bookId = bookRepository.create(book);

        authorsId.forEach(authorId -> bookAuthorRepository.create(bookId, authorId));

        book.setId(bookId);

        return book;
    }

    public PaginationWrapper<Book> listBooks(Integer limit, Integer page, String order, ListBooksFiltersRequest filtersRequest) {
        StatusEnum status = Objects.nonNull(filtersRequest.getStatus()) ? StatusEnum.fromValue(filtersRequest.getStatus().getValue()) : null;

        Integer total = bookRepository.countTotal(filtersRequest.getAuthor(), status,
                filtersRequest.getGenres(),
                filtersRequest.getReleaseYears());

        List<Book> books = bookRepository.list(limit, getOffset(page, limit), order, filtersRequest.getAuthor(), status,
                filtersRequest.getGenres(),
                filtersRequest.getReleaseYears());

        return new PaginationWrapper<>(page, limit, total, books);
    }

    public Book getBook(Integer id) {
        Book book = bookRepository.getBookByBookId(id).orElseThrow(() ->
                new BookNotFoundException("Book not found! Verify the id", HttpStatus.BAD_REQUEST));

        List<Author> authors = authorService.getAuthorsByBookId(book.getId());

        return book.addAuthors(authors);
    }

    public Book rentBook(Integer id) {
        Book book = this.getBook(id);

        book.rent();

        bookRepository.updateBookStatus(book);

        return book;
    }

    public Book returnBook(Integer id) {
        Book book = this.getBook(id);

        book.returning();

        bookRepository.updateBookStatus(book);

        return book;
    }

    public void deleteBook(Integer id) {
        Book book = this.getBook(id);

        book.delete();

        bookRepository.deleteBook(book);
    }

    private Integer getOffset(Integer page, Integer limit) {
        return (page - 1) * limit;
    }
}
