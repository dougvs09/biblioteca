package br.com.biblioteca.service;

import br.com.biblioteca.domain.JwtTokenInfo;
import br.com.biblioteca.domain.User;
import br.com.biblioteca.security.JwtTokenService;
import br.com.biblioteca.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    LoginService tested;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtTokenService jwtTokenService;

    @Test
    @DisplayName("Should do login")
    void shouldDoLogin() {
        LoginRequest loginRequest = getLoginRequest();
        JwtTokenInfo jwtTokenInfo = getJwtTokenInfo();
        Authentication authentication = getAuthentication();
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtTokenService.generateToken(any())).thenReturn(jwtTokenInfo);

        JwtTokenInfo result = tested.login(loginRequest);

        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtTokenService, times(1)).generateToken(any());
        assertAll("Asser result", () -> assertEquals(result, jwtTokenInfo));
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

    private Authentication getAuthentication() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return new UserDetailsImpl(getUser());
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "";
            }
        };
    }

    private User getUser() {
        return User.of("Name test", "email@teste.com", "test123");
    }
}
