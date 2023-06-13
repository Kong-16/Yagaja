package com.ssafy.enjoytrip.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.enjoytrip.user.model.User;
import com.ssafy.enjoytrip.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private final String tokenIssuer;
    private final int tokenLifetime;
    private final Algorithm cryptoAlgorithm;

    public JwtAuthenticationProvider(final UserService userService, final String tokenIssuer, final int tokenLifetime,
                                     final Algorithm cryptoAlgorithm) {
        this.userService = userService;
        this.tokenIssuer = tokenIssuer;
        this.tokenLifetime = tokenLifetime;
        this.cryptoAlgorithm = cryptoAlgorithm;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public int getTokenLifetime() {
        return tokenLifetime;
    }

    public Algorithm getCryptoAlgorithm() {
        return cryptoAlgorithm;
    }

    public void setUserService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(final Authentication auth) throws AuthenticationException {
        final String username = auth.getPrincipal().toString();
        final String password = auth.getCredentials().toString();

        final User user = userService.getUserById(username);

        if (user == null || !user.getPassword().equals(password)) {
            // TODO(ME): write more helpful exception message
            throw new BadCredentialsException("User credentials not matched.");
        }

        final List<String> userRoles = new ArrayList<>();
        userRoles.add(user.getRole().toString());

        final Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("name", user.getName());
        userInfo.put("roles", userRoles);

        final Instant now = Instant.now();
        final JwtAuthentication jwtAuth = new JwtAuthentication();
        jwtAuth.setAuthenticated(true);
        jwtAuth.addAuthority(user.getRole());
        jwtAuth.setName(user.getName());
        jwtAuth.setPrincipal(username);
        jwtAuth.setToken(JWT.create()
                .withIssuer(tokenIssuer)
                .withIssuedAt(now)
                .withSubject(username)
                .withExpiresAt(now.plusSeconds(tokenLifetime))
                .withClaim("client", userInfo)
                .sign(cryptoAlgorithm));
        return jwtAuth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public static JwtAuthenticationProviderBuilder builder() {
        return new JwtAuthenticationProviderBuilder();
    }

    public static class JwtAuthenticationProviderBuilder {

        private UserService userService;
        private String tokenIssuer;
        private int tokenLifetime;
        private Algorithm tokenCryptoAlgorithm;

        public JwtAuthenticationProviderBuilder setUserService(final UserService userService) {
            this.userService = userService;
            return this;
        }

        public JwtAuthenticationProviderBuilder setTokenIssuer(final String tokenIssuer) {
            this.tokenIssuer = tokenIssuer;
            return this;
        }

        public JwtAuthenticationProviderBuilder setTokenLifetime(final int tokenLifetime) {
            this.tokenLifetime = tokenLifetime;
            return this;
        }

        public JwtAuthenticationProviderBuilder setTokenCryptoAlgorithm(final Algorithm tokenCryptoAlgorithm) {
            this.tokenCryptoAlgorithm = tokenCryptoAlgorithm;
            return this;
        }

        public JwtAuthenticationProvider build() throws IllegalStateException {
            if (tokenIssuer == null) {
                throw new IllegalStateException("tokenIssuer must be specified.");
            }
            if (tokenLifetime == 0) {
                throw new IllegalStateException("tokenLifetime must be greater than zero.");
            }
            if (tokenCryptoAlgorithm == null) {
                throw new IllegalStateException("tokenCryptoAlgorithm must be specifed.");
            }
            return new JwtAuthenticationProvider(userService, tokenIssuer, tokenLifetime, tokenCryptoAlgorithm);
        }

    }

}
