package br.com.biblioteca.controller;

import br.com.biblioteca.domain.JwtTokenInfo;
import br.com.biblioteca.mapper.LoginResponseMapper;
import br.com.biblioteca.service.LoginService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    LoginController tested;
    @Mock
    LoginService loginService;
    @Mock
    LoginResponseMapper loginResponseMapper;

    @Test
    @DisplayName("Should do a login")
    void shouldDoALogin() {
        LoginRequest loginRequest = getLoginRequest();
        JwtTokenInfo jwtTokenInfo = getJwtTokenInfo();
        LoginResponse loginResponse = getLoginResponse();
        when(loginService.login(loginRequest)).thenReturn(jwtTokenInfo);
        when(loginResponseMapper.toResponse(jwtTokenInfo)).thenReturn(loginResponse);

        ResponseEntity<LoginResponse> result = tested.login(loginRequest);

        verify(loginService, times(1)).login(loginRequest);
        verify(loginResponseMapper, times(1)).toResponse(jwtTokenInfo);
        assertAll("Assert result and loginResponse", () -> {
           assertEquals(HttpStatus.OK, result.getStatusCode());
           assertEquals(result.getBody().getToken(), loginResponse.getToken());
        });
    }

    private LoginRequest getLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email@teste.com");
        loginRequest.setPassword("teste123");
        return loginRequest;
    }

    private JwtTokenInfo getJwtTokenInfo() {
        return JwtTokenInfo.of("tokenteste", Instant.now(), Instant.now());
    }

    private LoginResponse getLoginResponse() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(getJwtTokenInfo().getToken());
        loginResponse.setExpiresAt(getJwtTokenInfo().getExpirationDate().toString());
        loginResponse.setToken(getJwtTokenInfo().getCreationDate().toString());
        return loginResponse;
    }
}
