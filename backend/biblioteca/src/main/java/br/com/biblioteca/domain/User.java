package br.com.biblioteca.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User {

    @Setter
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Boolean active;

    private User(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = true;
    }

    public static User of(String name, String email, String password) {
        return new User(null, name, email, password);
    }

    public static User of(Integer id, String name, String email, String password) {
        return new User(id, name, email, password);
    }
}
