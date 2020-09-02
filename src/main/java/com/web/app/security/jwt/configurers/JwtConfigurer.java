package com.web.app.security.jwt.configurers;

import com.web.app.security.jwt.filters.JwtFilter;
import com.web.app.security.jwt.providers.JwtProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtProviderImpl JwtProviderImpl;

    @Autowired
    public JwtConfigurer(JwtProviderImpl JwtProviderImpl) {
        this.JwtProviderImpl = JwtProviderImpl;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        JwtFilter jwtTokenFilter = new JwtFilter(JwtProviderImpl);
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
