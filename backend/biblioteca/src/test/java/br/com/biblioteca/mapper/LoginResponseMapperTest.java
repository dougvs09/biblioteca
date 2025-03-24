package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.JwtTokenInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.LoginResponse;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LoginResponseMapperTest {

    LoginResponseMapper tested = new LoginResponseMapper();

    @Test
    @DisplayName("Should map to response")
    void shouldMapToResponse() {
        JwtTokenInfo jwtTokenInfo = getJwtTokenInfo();

        LoginResponse result = tested.toResponse(jwtTokenInfo);

        assertAll("Assert map result", () -> {
            assertEquals(result.getToken(), jwtTokenInfo.getToken());
            assertEquals(result.getCreatedAt(), jwtTokenInfo.getCreationDate().toString());
            assertEquals(result.getExpiresAt(), jwtTokenInfo.getExpirationDate().toString());
        });
    }

    private JwtTokenInfo getJwtTokenInfo() {
        return JwtTokenInfo.of("tokenteste", Instant.now(), Instant.now());
    }
}
