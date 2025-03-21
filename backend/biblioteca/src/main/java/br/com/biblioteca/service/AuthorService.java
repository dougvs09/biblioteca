package br.com.biblioteca.service;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author create(Author author) {
        return authorRepository.getAuthorByName(author.getName()).orElseGet(() -> {
            Integer id = authorRepository.create(author);

            author.setId(id);

            return author;
        });
    }

    public List<Author> getAuthorsByBookId(Integer bookId) {
        return authorRepository.getAuthorsByBookId(bookId);
    }
}
