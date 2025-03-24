package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.JwtTokenInfo;
import br.com.biblioteca.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.LoginResponse;
import org.openapitools.model.UserResponse;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserResponseMapperTest {

    UserResponseMapper tested = new UserResponseMapper();

    @Test
    @DisplayName("Should map to response")
    void shouldMapToResponse() {
        User user = getUser();

        UserResponse result = tested.toResponse(user);

        assertAll("Assert map result", () -> {
            assertEquals(result.getEmail(), user.getEmail());
            assertEquals(result.getName(), user.getName());
        });
    }

    private User getUser() {
        return User.of("Name test", "Email test", "Password test");
    }
}
