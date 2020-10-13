package com.web.app.rest;

import com.web.app.model.request.SignUpRequestDTO;
import com.web.app.service.CommonUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SignUpController {

    private final CommonUsersService commonUsersService;

    @Autowired
    public SignUpController(CommonUsersService commonUsersService) {
        this.commonUsersService = commonUsersService;
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        boolean save = commonUsersService.save(signUpRequestDTO);
        if (save) {
            //todo написать логику
        } else {
            //todo написать логику
        }
    }
}
