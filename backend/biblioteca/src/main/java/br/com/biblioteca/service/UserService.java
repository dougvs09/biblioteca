package br.com.biblioteca.service;

import br.com.biblioteca.configuration.SecurityConfiguration;
import br.com.biblioteca.domain.User;
import br.com.biblioteca.exception.UserAlreadyExistsException;
import br.com.biblioteca.exception.UserNotFoundException;
import br.com.biblioteca.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SecurityConfiguration securityConfiguration;
    private final UserRepository userRepository;

    @Transactional
    public User create(CreateUserRequest request) {
        userRepository.getUserByEmail(request.getEmail()).ifPresent(v -> {
            throw new UserAlreadyExistsException("An user with this email already exists!", HttpStatus.BAD_REQUEST);
        });

        String passwordEncoded = securityConfiguration.passwordEncoder().encode(request.getPassword());

        User user = User.of(request.getName(), request.getEmail(), passwordEncoded);

        Integer id = userRepository.create(user);

        user.setId(id);

        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User with this email not found!", HttpStatus.NOT_FOUND));
    }
}
