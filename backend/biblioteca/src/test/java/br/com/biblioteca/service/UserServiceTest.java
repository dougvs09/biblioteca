package br.com.biblioteca.service;

import br.com.biblioteca.configuration.SecurityConfiguration;
import br.com.biblioteca.domain.User;
import br.com.biblioteca.exception.UserAlreadyExistsException;
import br.com.biblioteca.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService tested;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    SecurityConfiguration securityConfiguration;

    @Test
    @DisplayName("Should create an user")
    void shouldCreateAnUser() {
        CreateUserRequest createUserRequest = getCreateUserRequest();
        Integer id = 1;
        when(userRepository.getUserByEmail(any())).thenReturn(Optional.empty());
        when(securityConfiguration.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        when(userRepository.create(any())).thenReturn(id);

        User result = tested.create(createUserRequest);

        verify(userRepository, times(1)).getUserByEmail(any());
        verify(securityConfiguration, times(1)).passwordEncoder();
        verify(userRepository, times(1)).create(any());
        assertAll("Assert result", () -> {
            assertNotNull(result.getId());
        });
    }

    @Test
    @DisplayName("Should throw exception when user already exists")
    void shouldThrowExceptionWhenUserAlreadyExists() {
        CreateUserRequest createUserRequest = getCreateUserRequest();
        User user = getUser();
        when(userRepository.getUserByEmail(any())).thenReturn(Optional.of(user));

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> tested.create(createUserRequest));

        verify(userRepository, times(1)).getUserByEmail(any());
        verify(securityConfiguration, times(0)).passwordEncoder();
        verify(userRepository, times(0)).create(any());
        assertAll("Assert result", () -> {
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            assertEquals("An user with this email already exists!", exception.getMessage());
            assertNull(user.getId());
        });
    }

    private CreateUserRequest getCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName("Name test");
        createUserRequest.setEmail("Email test");
        createUserRequest.setPassword("Password test");
        return createUserRequest;
    }

    private User getUser() {
        return User.of("Name test", "Email test", "Password test");
    }
}
