package com.web.app.rest;

import com.web.app.entity.UsersEntity;
import com.web.app.model.AuthenticationRequestDTO;
import com.web.app.security.jwt.providers.JwtProviderImpl;
import com.web.app.service.DefaultUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProviderImpl JwtProviderImplImpl;
    private final DefaultUsersService defaultUsersService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtProviderImpl JwtProviderImplImpl,
                                    DefaultUsersService defaultUsersService) {
        this.authenticationManager = authenticationManager;
        this.JwtProviderImplImpl = JwtProviderImplImpl;
        this.defaultUsersService = defaultUsersService;
    }

    public ResponseEntity login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        String username = authenticationRequestDTO.getUsername();
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDTO.getPassword()));
        UsersEntity usersEntity = defaultUsersService.findByName(username);

        if (usersEntity == null) {
            throw new UsernameNotFoundException("User with " + username + " - username not found");
        }

        String token = JwtProviderImplImpl.provideToken(username, usersEntity.getRoles());
        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public ModelAndView login() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return new ModelAndView("login.html");
    }
}
