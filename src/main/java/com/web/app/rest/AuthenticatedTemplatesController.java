package com.web.app.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticatedTemplatesController {

    @PostMapping
    public ModelAndView welcomeAuthenticated() {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
}
