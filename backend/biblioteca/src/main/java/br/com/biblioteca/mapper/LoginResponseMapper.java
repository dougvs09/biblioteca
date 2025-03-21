package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.JwtTokenInfo;
import org.openapitools.model.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public class LoginResponseMapper {

    public LoginResponse toResponse(JwtTokenInfo jwtTokenInfo) {
        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(jwtTokenInfo.getToken());
        loginResponse.setCreatedAt(jwtTokenInfo.getCreationDate().toString());
        loginResponse.setExpiresAt(jwtTokenInfo.getExpirationDate().toString());

        return loginResponse;
    }
}
