package com.web.app.rest;

import com.web.app.entity.UsersEntity;
import com.web.app.model.AuthenticationRequestDTO;
import com.web.app.security.jwt.providers.JwtProvider;
import com.web.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//TODO: \ДЛЯ ВСЕХ КОНТРОЛЛЕРОВ\ - ЕЩЕ ИХ НЕ РАЗБИРАЛ
@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtProvider jwtProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    public ResponseEntity login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        String username = authenticationRequestDTO.getLogin();
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDTO.getPassword()));
        UsersEntity usersEntity = userService.findByUsername(username);

        if (usersEntity == null) {
            throw new UsernameNotFoundException("User with " + username + " - username not found");
        }

        String token = jwtProvider.createToken(username, usersEntity.getRoles());
        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
