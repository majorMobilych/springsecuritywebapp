package com.web.app.security.jwt.configurers;

import com.web.app.security.jwt.filters.JwtFilter;
import com.web.app.security.jwt.providers.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtProvider jwtProvider;

    //TODO: почему подсвечивается автоваерд?
    @Autowired
    public JwtConfigurer(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        JwtFilter jwtTokenFilter = new JwtFilter(jwtProvider);
        //TODO: (для меня) почитать доку.
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
