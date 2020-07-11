package com.web.app.model;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String login;
    private String password;
}
