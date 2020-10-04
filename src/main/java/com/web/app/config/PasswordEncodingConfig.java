package com.web.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class, storing password encoder.
 */
@Configuration
/*
 *  EXPLANATION: @PropertySource resolves property-file, which contains a property, needed to configure passwordEncoder,
 *              namely: 'password.encoder.strength'.
 */
@PropertySource("properties/security/passwordencoder.properties")
public class PasswordEncodingConfig {

    /*
     *  EXPLANATION: @Value("${...}") injects property, named as declared in '{}', i.e. 'password.encoder.strength'.
     */
    @Value("${password.encoder.strength}")
    private Integer encodingStrength;

    /*
     *  EXPLANATION: I need password encoder for saving encoded user's password in my database. It is recommended
     *              to prevent the data loss in case of database hacking - attacker won't be able to use user's data in
     *              his purpose.
     *                  The idea is straight forward: if the attacker hacked our database and tries to login as a user,
     *              (he inputs someone's username and{encoded} password). Then, this username and password goes to
     *              server, inputted{already encoded password, hence he took it from my database, gets encoded one more
     *              time} and it is almost impossible that the real password and the one, entered by attacker will
     *              coincide.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(encodingStrength);
    }
}
