package br.com.biblioteca.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Book {

    @Setter
    private Integer id;
    private String name;
    private String resume;
    private String releaseYear;
    private String genre;
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
}
