package br.com.biblioteca.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Author {

    @Setter
    private Integer id;
    private final String name;
    private final boolean active;

    private Author(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.active = true;
    }

    public static Author of(String name) {
        return new Author(null, name);
    }

    public static Author of(Integer id, String name) {
        return new Author(id, name);
    }
}
