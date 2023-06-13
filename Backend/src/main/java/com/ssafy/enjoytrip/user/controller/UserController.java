package com.ssafy.enjoytrip.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssafy.enjoytrip.user.dto.UserJoinDto;
import com.ssafy.enjoytrip.user.dto.UserModifyDto;
import com.ssafy.enjoytrip.user.model.User;
import com.ssafy.enjoytrip.user.repository.UserRepository;
import com.ssafy.enjoytrip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @PostMapping("/join")
    public ResponseEntity<Void> addUser(@RequestBody final UserJoinDto userJoinDto) {

        try {
            if (userService.addUser(userJoinDto)) {
                return ResponseEntity.status(HttpStatus.CREATED.value()).build();
            }
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal || hasRole('ADMIN')")
    public ResponseEntity<String> getUser(@PathVariable final String userId) {
        final User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<> getUserByUserId(@PathVariable final String userId,
//                                            @RequestParam(required = false) final String info) {
//        userRepository.findNameById()
//    }

    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal || hasRole('ADMIN')")
    public ResponseEntity<Void> updateUser(@PathVariable final String userId, @RequestBody UserModifyDto userModifyDto) {
        if (userService.modifyUser(userId, userModifyDto)) {

        }


        // 1. userService.updateUser(user);  <- ?
        // 2. userService.updateUser(user, patches);  <- 재사용성 ?

        // what is better ?

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal || hasRole('ADMIN')")
    public void deleteUser(@PathVariable final String userId, HttpServletResponse resp) {

        if (userService.removeUser(userId)) {
            final Cookie tokenCookie = new Cookie("token", "");
            tokenCookie.setHttpOnly(true);
            tokenCookie.setMaxAge(0);
            tokenCookie.setPath("/");
            resp.addCookie(tokenCookie);
            return;
        }

        resp.setStatus(HttpStatus.BAD_REQUEST.value());
    }

}
