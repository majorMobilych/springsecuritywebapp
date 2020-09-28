package com.web.app.rest;

import com.web.app.model.AuthenticationRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    //TODO: переписать контроллер + пересмотреть код амиго. Не выкупаю, как аутентифицировать пользователя.
    @PostMapping("/all/login")
    public String login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        String name = authenticationRequestDTO.getName();
        String password = authenticationRequestDTO.getPassword();

        return "success";
    }
}

