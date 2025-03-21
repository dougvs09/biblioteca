package br.com.biblioteca.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Author {

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

    private Author(String name) {
        this.name = name;
        this.active = true;
    }

    public static Author of(Integer id, String name, List<Book> books) {
        return new Author(id, name, books);
    }

    public static Author of(String name) {
        return new Author(name);
    }
}
