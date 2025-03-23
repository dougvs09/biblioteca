package br.com.biblioteca.security;

import br.com.biblioteca.domain.JwtTokenInfo;
import br.com.biblioteca.domain.User;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenServiceTest {

    @InjectMocks
    JwtTokenService tested;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(tested, "secret", "fouefiewbifuw3453");
    }

    @Test
    @DisplayName("Should create a token")
    void shouldCreateAToken() {
        UserDetailsImpl userDetails = getUserDetails();

        JwtTokenInfo result = tested.generateToken(userDetails);

        assertAll("Assert result", () -> {
            assertNotNull(result.getToken());
        });
    }

    @Test
    @DisplayName("Should get a subject")
    void shouldGetASubject() {
        UserDetailsImpl userDetails = getUserDetails();
        JwtTokenInfo jwtTokenInfo = tested.generateToken(userDetails);

        String result = tested.getSubjectFromToken(jwtTokenInfo.getToken());

        assertAll("Assert result", () -> {
            assertNotNull(result);
        });
    }

    private User getUser() {
        return User.of("Name test", "email@teste.com", "test123");
    }

    private UserDetailsImpl getUserDetails() {
        return new UserDetailsImpl(getUser());
    }
}
