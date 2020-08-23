package com.web.app.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequestDTO {

    private String email;
    private String password;
}
