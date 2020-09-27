package com.web.app.rest;

import com.web.app.model.AuthenticationRequestDTO;
import com.web.app.service.CommonUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CommonUsersService commonUsersService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    CommonUsersService commonUsersService) {
        this.authenticationManager = authenticationManager;
        this.commonUsersService = commonUsersService;
    }

    @PostMapping("/all/login")
    public String login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        String name = authenticationRequestDTO.getName();
        String password = authenticationRequestDTO.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));

        return "success";
    }
}

