package br.com.biblioteca.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Author {

    @Setter
    private Integer id;
    private String name;
    private List<Book> books;
    private boolean active;

    private Author(Integer id, String name, List<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
        this.active = true;
    }

    public static Author of(Integer id, String name, List<Book> books) {
        return new Author(id, name, books);
    }

    public static Author of(String name) {
        return new Author(null, name, null);
    }

    public static Author of(Integer id, String name) {
        return new Author(id, name, null);
    }
}
