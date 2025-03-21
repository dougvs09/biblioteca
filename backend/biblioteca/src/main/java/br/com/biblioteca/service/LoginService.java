package br.com.biblioteca.service;

import br.com.biblioteca.domain.JwtTokenInfo;
import br.com.biblioteca.security.JwtTokenService;
import br.com.biblioteca.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public JwtTokenInfo login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtTokenService.generateToken(userDetails);
    }
}
