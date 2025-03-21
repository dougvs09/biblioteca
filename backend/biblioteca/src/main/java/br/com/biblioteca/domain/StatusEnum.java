package br.com.biblioteca.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {

    AVAILABLE("Available"), UNAVAILABLE("Unavailable");

    private final String value;
}
