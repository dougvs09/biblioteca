package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.User;
import org.openapitools.model.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }
}
