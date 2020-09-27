package com.web.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class, storing password encoder.
 */
@Configuration
public class PasswordEncodingConfig {

    /*
     *  EXPLANATION: I need password encoder to encode user's password for saving it in my database. It is recommended
     *              to prevent the data loss in case of database hacking - attacker won't be able to use user's data in
     *              his purpose.
     *              TODO: нарисовать схему про кодировку пароля и аутентификацию.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
