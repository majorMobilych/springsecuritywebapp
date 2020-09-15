package com.web.app.rest;

import com.web.app.entity.UsersEntity;
import com.web.app.model.AuthenticationRequestDTO;
import com.web.app.security.jwt.providers.JwtProvider;
import com.web.app.service.CommonUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final CommonUsersService commonUsersService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtProvider jwtProvider,
                                    CommonUsersService commonUsersService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.commonUsersService = commonUsersService;
    }

    @PostMapping("/misha/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        String name = authenticationRequestDTO.getName();
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(name, authenticationRequestDTO.getPassword()));
        UsersEntity usersEntity = commonUsersService.findByName(name);

        if (usersEntity == null) {
            throw new UsernameNotFoundException("User with '" + name + "' - name not found");
        }

        String token = jwtProvider.provideToken(name, usersEntity.getRoles());
        Map<Object, Object> response = new HashMap<>();
        response.put("name", name);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}

