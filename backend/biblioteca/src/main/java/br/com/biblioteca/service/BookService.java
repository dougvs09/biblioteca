package br.com.biblioteca.service;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.domain.Book;
import br.com.biblioteca.repository.AuthorRepository;
import br.com.biblioteca.repository.BookAuthorRepository;
import br.com.biblioteca.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.CreateBookRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookAuthorRepository bookAuthorRepository;

    @Transactional
    public Book create(CreateBookRequest request) {
        List<Author> authors = request.getAuthors().stream().map(Author::of).toList();
        Book book = Book.of(request.getName(), request.getResume(), request.getReleaseYear(), request.getGenre(), authors);

        List<Integer> authorsId = authors.stream().map(authorRepository::create).toList();
        Integer bookId = bookRepository.create(book);
        authorsId.forEach(authorId -> bookAuthorRepository.create(bookId, authorId));

        book.setId(bookId);

        return book;
    }
}
