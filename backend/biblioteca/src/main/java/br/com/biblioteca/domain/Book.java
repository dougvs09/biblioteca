package br.com.biblioteca.domain;

import br.com.biblioteca.exception.BookCannotBeDeletedException;
import br.com.biblioteca.exception.BookCannotBeRentedException;
import br.com.biblioteca.exception.BookCannotBeReturnedException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Book {

    @Setter
    private Integer id;
    private final String name;
    private final String resume;
    private final String releaseYear;
    private final String genre;
    private List<Author> authors;
    private StatusEnum status;
    private boolean active;

    private Book(Integer id, String name, String resume, String releaseYear, String genre, List<Author> authors) {
        this.id = id;
        this.name = name;
        this.resume = resume;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.authors = authors;
        this.active = true;
        this.status = StatusEnum.AVAILABLE;
    }

    private Book(Integer id, String name, String resume, String releaseYear, String genre, List<Author> authors, StatusEnum status) {
        this.id = id;
        this.name = name;
        this.resume = resume;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.authors = authors;
        this.status = status;
        this.active = true;
    }

    public static Book of(Integer id, String name, String resume, String releaseYear, String genre, List<Author> authors) {
        return new Book(id, name, resume, releaseYear, genre, authors);
    }

    public static Book of(String name, String resume, String releaseYear, String genre, List<Author> authors) {
        return new Book(null, name, resume, releaseYear, genre, authors);
    }

    public static Book of(Integer id, String name, StatusEnum status) {
        return new Book(id, name, null, null, null, null, status);
    }

    public static Book of(Integer id, String name, String resume, String releaseYear, String genre, StatusEnum status) {
        return new Book(id, name, resume, releaseYear, genre, null, status);
    }

    public Book addAuthors(List<Author> authors) {
        if (Objects.isNull(this.authors)) {
            this.authors = new ArrayList<>();
        }

        this.authors.addAll(authors);

        return this;
    }

    public void rent() {
        if (this.status == StatusEnum.UNAVAILABLE) {
            throw new BookCannotBeRentedException("This book cannot be rented because it's rented now", HttpStatus.BAD_REQUEST);
        }

        this.status = StatusEnum.UNAVAILABLE;
    }

    public void returning() {
        if (this.status == StatusEnum.AVAILABLE) {
            throw new BookCannotBeReturnedException("This book cannot be returned because it's available now", HttpStatus.BAD_REQUEST);
        }

        this.status = StatusEnum.AVAILABLE;
    }

    public void delete() {
        if (this.status == StatusEnum.UNAVAILABLE) {
            throw new BookCannotBeDeletedException("This book cannot be returned because it's rented now", HttpStatus.BAD_REQUEST);
        }

        this.active = false;
    }
}
