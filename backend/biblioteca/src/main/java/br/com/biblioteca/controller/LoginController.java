package br.com.biblioteca.controller;

import br.com.biblioteca.domain.JwtTokenInfo;
import br.com.biblioteca.mapper.LoginResponseMapper;
import br.com.biblioteca.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.LoginApi;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class LoginController implements LoginApi {

    private final LoginService loginService;
    private final LoginResponseMapper loginResponseMapper;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        JwtTokenInfo jwtTokenInfo = loginService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseMapper.toResponse(jwtTokenInfo));
    }
}
