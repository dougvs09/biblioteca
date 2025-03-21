package br.com.biblioteca.security;

import br.com.biblioteca.domain.JwtTokenInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    @Value("${app.security.jwt.secret}")
    private String secret;

    public JwtTokenInfo generateToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = this.getAlgorithm();
            Instant creationDate = this.creationDate();
            Instant expirationDate = this.expirationDate();

            String jwtToken = JWT.create()
                    .withIssuedAt(creationDate)
                    .withExpiresAt(expirationDate)
                    .withSubject(user.getUsername())
                    .sign(algorithm);

            return JwtTokenInfo.of(jwtToken, creationDate, expirationDate);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Token cannot be generated,", e);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = this.getAlgorithm();

            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid token or expired.", e);
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(1).toInstant();
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }
}
