package br.com.biblioteca.security;

import br.com.biblioteca.configuration.SecurityConfiguration;
import br.com.biblioteca.domain.User;
import br.com.biblioteca.exception.JwtTokenException;
import br.com.biblioteca.exception.UserNotFoundException;
import br.com.biblioteca.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!endpointIsPublic(request)) {
                String jwtToken = getJwtToken(request);

                String subject = jwtTokenService.getSubjectFromToken(jwtToken);
                User user = userRepository.getUserByEmail(subject).orElseThrow(() -> new UserNotFoundException("User with this email not found!", HttpStatus.NOT_FOUND));
                UserDetailsImpl userDetails = new UserDetailsImpl(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                        userDetails.getPassword(), userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    private String getJwtToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (Objects.isNull(authorizationHeader)) {
            throw new JwtTokenException("The authorization token is necessary for this endpoint", HttpStatus.UNAUTHORIZED);
        }

        return authorizationHeader.replace("Bearer ", "");
    }

    private boolean endpointIsPublic(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(uri);
    }
}
