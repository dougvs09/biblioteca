package br.com.biblioteca.controller;

import br.com.biblioteca.domain.User;
import br.com.biblioteca.mapper.UserResponseMapper;
import br.com.biblioteca.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.CreateUserRequest;
import org.openapitools.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController tested;
    @Mock
    UserService userService;
    @Mock
    UserResponseMapper userResponseMapper;

    @Test
    @DisplayName("Should create an user")
    void shouldCreateAnUser() {
        CreateUserRequest createUserRequest = getCreateUserRequest();
        User user = getUser();
        UserResponse userResponse = getUserResponse();
        when(userService.create(createUserRequest)).thenReturn(user);
        when(userResponseMapper.toResponse(user)).thenReturn(userResponse);

        ResponseEntity<UserResponse> result = tested.createUser(createUserRequest);

        verify(userService, times(1)).create(createUserRequest);
        verify(userResponseMapper, times(1)).toResponse(user);
        assertAll("Assert result and userResponse", () -> {
            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertEquals(result.getBody().getName(), userResponse.getName());
            assertEquals(result.getBody().getEmail(), userResponse.getEmail());
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

    private UserResponse getUserResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail("Email test");
        userResponse.setName("Name test");
        return userResponse;
    }
}
