package com.ssafy.enjoytrip.auth.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssafy.enjoytrip.auth.jwt.JwtAuthentication;
import com.ssafy.enjoytrip.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class JwtAuthController {

    @Value("${jwt.lifetime}")
    private int tokenLifetime;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public JwtAuthController(final UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping("/auth/probe")
    public ResponseEntity<?> probe(final Authentication auth) throws IOException {
        final ObjectNode retRootNode = objectMapper.createObjectNode();
        retRootNode.put("authenticated", false);
        retRootNode.put("userInfo", (JsonNode) null);

        if (auth != null && auth.isAuthenticated()) {
            final ObjectNode userInfoNode = objectMapper.createObjectNode();
            userInfoNode.put("id", auth.getPrincipal().toString());
            userInfoNode.put("name", auth.getName());

            retRootNode.put("authenticated", true);
            retRootNode.put("userInfo", userInfoNode);
        }

        return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(objectMapper.writeValueAsString(retRootNode));
    }

    @PostMapping("/auth/remove")
    @Secured("ROLE_USER")
    public ResponseEntity<?> remove(final HttpServletRequest req, final HttpServletResponse res, final Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        userService.removeUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/auth/logout")
    @Secured("ROLE_USER")
    public ResponseEntity<?> logout(final HttpServletRequest req, final HttpServletResponse res, final Authentication authentication) {

        final Cookie tokenCookie = new Cookie("token", "");
        tokenCookie.setHttpOnly(true);
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath("/");
        res.addCookie(tokenCookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void afterSignin(final HttpServletRequest req, final HttpServletResponse resp, final Authentication auth)
            throws IllegalArgumentException, IOException {
        if (!(auth instanceof JwtAuthentication)) {
            // TODO(ME): write helpful exception message
            throw new IllegalArgumentException("");
        }

        final ObjectNode root = objectMapper.createObjectNode();
        root.put("authenticated", true);
        root.put("message", "로그인에 성공하였습니다.");

        final Cookie tokenCookie = new Cookie("token", ((JwtAuthentication) auth).getToken());
        tokenCookie.setHttpOnly(true);
        tokenCookie.setMaxAge(tokenLifetime);
        tokenCookie.setPath("/");
        tokenCookie.setSecure(false);

        resp.setStatus(HttpStatus.OK.value());
        resp.addCookie(tokenCookie);
        resp.setHeader("Content-Type", "application/json;charset=utf-8");
        resp.getWriter().write(objectMapper.writeValueAsString(root));
    }

    public void afterSigninFailure(final HttpServletRequest req, final HttpServletResponse resp, final AuthenticationException authException) throws IOException {
        if (authException instanceof BadCredentialsException) {
            final ObjectNode root = objectMapper.createObjectNode();
            root.put("authenticated", false);
            root.put("message", "회원 정보가 일치하지 않습니다.");

            resp.setStatus(HttpStatus.OK.value());
            resp.setHeader("Content-Type", "application/json;charset=utf-8");
            resp.getWriter().write(objectMapper.writeValueAsString(root));
        } else {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
