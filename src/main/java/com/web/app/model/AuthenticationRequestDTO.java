package com.web.app.model;

import lombok.*;

/**
 * A DTO class, representing a login request, i.e. name and password.
 */
/*
 *  NOTE: Need no-args constructor for any DTO class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequestDTO {

    private String name;
    private String password;
}
