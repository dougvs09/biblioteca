package br.com.biblioteca.domain;

import lombok.Getter;

import java.time.Instant;

@Getter
public class JwtTokenInfo {

    private final String token;
    private final Instant creationDate;
    private final Instant expirationDate;

    private JwtTokenInfo(String token, Instant creationDate, Instant expirationDate) {
        this.token = token;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public static JwtTokenInfo of(String token, Instant creationDate, Instant expirationDate) {
        return new JwtTokenInfo(token, creationDate, expirationDate);
    }
}
