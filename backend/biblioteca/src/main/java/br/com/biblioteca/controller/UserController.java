package br.com.biblioteca.controller;

import br.com.biblioteca.domain.User;
import br.com.biblioteca.mapper.UserResponseMapper;
import br.com.biblioteca.service.UserService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.UserApi;
import org.openapitools.model.CreateUserRequest;
import org.openapitools.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController implements UserApi {

    private final UserService userService;
    private final UserResponseMapper userResponseMapper;

    @Override
    public ResponseEntity<UserResponse> createUser(CreateUserRequest createUserRequest) {
        User user = userService.create(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseMapper.toResponse(user));
    }
}
