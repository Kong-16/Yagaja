package com.ssafy.enjoytrip.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.ssafy.enjoytrip.auth.controller.JwtAuthController;
import com.ssafy.enjoytrip.auth.jwt.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.enjoytrip.auth.jwt.JwtCookieFilter;
import com.ssafy.enjoytrip.user.service.UserService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class JwtLoginConfiguration {

    @Autowired
    private Environment environment;
    private Algorithm cryptoAlgorithm;

    public JwtLoginConfiguration() throws NoSuchAlgorithmException {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);

        final KeyPair keyPair = keyPairGenerator.generateKeyPair();
        cryptoAlgorithm = Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());
    }

    private HttpSecurity registerJwtAuthenticationProvider(final HttpSecurity http, final UserService userService) {
        final String tokenIssuer = environment.getProperty("jwt.issuer");
        final int tokenLifetime = Integer.parseInt(environment.getProperty("jwt.lifetime"));
        http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(JwtAuthenticationProvider.builder()
                        .setUserService(userService)
                        .setTokenIssuer(tokenIssuer)
                        .setTokenLifetime(tokenLifetime)
                        .setTokenCryptoAlgorithm(cryptoAlgorithm)
                        .build());
        return http;
    }

    @Bean
    public SecurityFilterChain filterChain(
            final HttpSecurity http,
            final UserService userService,
            final JwtAuthController authController) throws Exception {
        registerJwtAuthenticationProvider(http, userService);

        return http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtCookieFilter(cryptoAlgorithm), LogoutFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/auth/signout")
                        .deleteCookies("token")
                        .clearAuthentication(true))
                .formLogin(form -> form
                        .loginPage("/auth/signin")
                        .successHandler(authController::afterSignin)
                        .failureHandler(authController::afterSigninFailure))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .sessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy()))
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
